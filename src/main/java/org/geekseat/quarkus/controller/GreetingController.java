package org.geekseat.quarkus.controller;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.geekseat.quarkus.model.Fruit;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/system/monitor")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingController {

    @GET
    @Path("/ping")
    public Uni<String> ping() {
        return Fruit.listAll()
                .map(entityBases -> entityBases.isEmpty() ? "error" : "ok");
    }

    @GET
    @Path("/stream/test")
    @Produces(MediaType.APPLICATION_JSON_PATCH_JSON)
    public Multi<Integer> streamTest() {
        return Multi.createFrom().items(1, 2, 3, 4, 5)
                .onItem().call(i -> {
                    Log.info("Ping i: {}" + i);
                    Duration delay = Duration.ofSeconds(1);
                    return Uni.createFrom().nullItem().onItem().delayIt().by(delay);
                });
    }

    @GET
    @Path("/hello")
    public Uni<String> helloWorld() {
        Uni<String> hello = Uni.createFrom().item("hello")
                .onItem().transform(item -> item + " mutiny")
                .onItem().transform(String::toUpperCase);
        hello.subscribe().with(
                item -> System.out.println("subscribe hello item >> " + item));
        return hello;
    }
}
