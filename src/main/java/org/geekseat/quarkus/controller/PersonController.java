package org.geekseat.quarkus.controller;


import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import org.geekseat.quarkus.model.Person;
import org.geekseat.quarkus.service.PersonService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.ResponseBuilder;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/person")
@RequestScoped
@RolesAllowed("admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {

    @Inject
    PersonService personService;

    @POST
    public Uni<Response> save(Person person) {
        person.createdBy();
        return Panache.withTransaction(() ->
                        personService.persist(personService.toDecorator.decorate(person)))
                .map(created -> Response.created(URI.create("/person" + created.getId())).build());
    }

    @PUT
    public Uni<Response> update(Person person) {
        if (person.getId() == null) {
            return Uni.createFrom().item(Response.status(BAD_REQUEST))
                    .map(ResponseBuilder::build);
        }
        person.updatedBy();
        return Panache.withTransaction(() -> personService.update(personService.toDecorator.decorate(person)))
                .map(created -> Response.ok(created).build());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> personById(Long id) {
        return personService.findById(id)
                .map(person -> person == null ? Response.status(NOT_FOUND) : Response.ok(personService.fromDecorator.decorate(person)))
                .map(ResponseBuilder::build);
    }

    @GET
    @Path("/list")
    public Multi<Person> list() {
        return personService.listAll()
                .onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
                .map(personService.fromDecorator::decorate);
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
                .map(personService.fromDecorator::decorate);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Panache.withTransaction(() -> personService.deleteById(id)
                .map(deleted -> deleted ? Response.noContent() : Response.notModified())
                .map(ResponseBuilder::build));
    }

    @GET
    @Path("/datatables")
    public Uni<Tuple2<List<Person>, Long>> datatables(@QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize) {
        return Panache.withTransaction(() -> Uni.combine().all()
                .unis(personService.findAll().page(pageIndex, pageSize).list()
                        .onItem().transformToMulti(persons -> Multi.createFrom().iterable(persons))
                        .map(personService.fromDecorator::decorate).collect().asList(),
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
