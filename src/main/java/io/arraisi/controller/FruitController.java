package io.arraisi.controller;

import io.arraisi.model.Fruit;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Slf4j
@Path("/fruits")
@ApplicationScoped
public class FruitController {

    @Inject
    MySQLPool client;

    @GET
    @Path("/query/list")
    public Multi<Fruit> findAll() {
        return Fruit.findAll(client);
    }

    @GET
    @Path("/query/uni/list")
    public Uni<List<Fruit>> list() {
        return Fruit.list(client);
    }

    @GET
    @Path("/query/{id}")
    public Uni<Fruit> findById(Long id) {
        return Fruit.findById(client, id);
    }

    @PUT
    @Path("/query/update")
    public Uni<Boolean> update(Fruit fruit) {
        return Panache.withTransaction(() -> Fruit.update(client, fruit));
    }

    @GET
    public Uni<List<Fruit>> get() {
        return Fruit.listAll(Sort.by("name"));
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getSingle(Long id) {
        return Fruit.findById(client, id)
                .onItem().transform(fruit -> fruit != null ? Response.ok(fruit) : Response.status(NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(Fruit fruit) {
        return Panache.<Fruit>withTransaction(fruit::persist)
                .onItem()
                .transform(this::responseCreated);
    }

    private Response responseCreated(Fruit fruit) {
        log.info("created id: {}", fruit.id);
        log.info("created name: {}", fruit.name);
        return Response.created(URI.create("/fruits/" + fruit.id)).build();
    }
}
