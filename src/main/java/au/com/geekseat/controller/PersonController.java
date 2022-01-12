package au.com.geekseat.controller;

import au.com.geekseat.model.Person;
import au.com.geekseat.service.PersonService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static au.com.geekseat.service.PersonService.fromDecorator;
import static au.com.geekseat.service.PersonService.toDecorator;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.*;

@Slf4j
@Path("/person")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {

    @Inject
    PersonService personService;

    @POST
    public Uni<Response> save(Person person) {
        person.createdBy();
        return Panache.withTransaction(() -> personService.persist(toDecorator.decorate(person)))
                .map(created -> created(URI.create("/person" + created.getId())).build());
    }

    @PUT
    public Uni<Response> update(Person person) {
        if (person.getId() == null) {
            return Uni.createFrom().item(status(BAD_REQUEST))
                    .map(ResponseBuilder::build);
        }
        person.updatedBy();
        return Panache.withTransaction(() -> personService.update(toDecorator.decorate(person)))
                .map(created -> ok(created).build());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> personById(Long id) {
        return personService.findById(id)
                .map(person -> person == null ? status(NOT_FOUND) : ok(fromDecorator.decorate(person)))
                .map(ResponseBuilder::build);
    }

    @GET
    @Path("/list")
    public Multi<Person> list() {
        return personService.listAll()
                .onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
                .map(fromDecorator::decorate);
    }

    @GET
    @Path("/list/sort/name")
    public Uni<List<Person>> listSoreByName() {
        return personService.listAll(Sort.by("name"));
    }

    @GET
    @Path("/list/active")
    public Multi<Person> listActive() {
        return personService.list("active", true)
                .onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
                .map(fromDecorator::decorate);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Panache.withTransaction(() -> personService.deleteById(id)
                .map(deleted -> deleted ? noContent() : notModified())
                .map(ResponseBuilder::build));
    }

    @GET
    @Path("/datatables")
    public Uni<Tuple2<List<Person>, Long>> datatables(@QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize) {
        return Panache.withTransaction(() -> Uni.combine().all()
                .unis(personService.findAll().page(pageIndex, pageSize).list()
                        .onItem().transformToMulti(persons -> Multi.createFrom().iterable(persons))
                        .map(fromDecorator::decorate).collect().asList(),
                personService.count())
                .asTuple());
    }

    @GET
    @Path("/query/{id}")
    public Uni<Person> queryUniById(Long id) {
        return personService.queryFindById(id);
    }

    @GET
    @Path("/query/list")
    public Multi<Person> queryMultiListAll() {
        return personService.queryPersonMultiList();
    }

    @GET
    @Path("/query/uni/list")
    public Uni<List<Person>> queryUniListAll() {
        return personService.queryPersonUniList();
    }
}
