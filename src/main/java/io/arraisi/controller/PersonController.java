package io.arraisi.controller;

import io.arraisi.model.Person;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Slf4j
@Path("/person")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {

    @GET
    @Path("/{id}")
    public Uni<Person> personById(Long id) {
        return Person.findById(id)
                .onItem().transform(Person::fromDecorator);
    }

    @GET
    @Path("/list")
    public Multi<Person> findAll() {
        return Person.listAll()
                .onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
                .onItem().transform(Person::fromDecorator);
    }
}
