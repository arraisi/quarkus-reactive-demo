package io.arraisi.controller;

import io.arraisi.model.Shop;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/shop")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShopController {
    @GET
    @Path("/{id}")
    public Uni<Shop> roleById(Long id) {
        return Shop.findById(id);
    }
}