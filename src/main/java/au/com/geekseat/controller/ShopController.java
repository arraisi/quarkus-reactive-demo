package au.com.geekseat.controller;

import au.com.geekseat.model.Shop;
import au.com.geekseat.service.PocketService;
import au.com.geekseat.service.ProductService;
import au.com.geekseat.service.ShopService;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.quarkus.hibernate.reactive.panache.Panache.withTransaction;
import static javax.ws.rs.core.Response.*;

@Path("/shop")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShopController {
    @Inject
    ShopService shopService;
    @Inject
    ProductService productService;
    @Inject
    PocketService pocketService;

    @GET
    @Path("/{id}")
    public Uni<Shop> shopById(Long id) {
        return shopService.findById(id);
    }

    @PUT
    @Path("/checkout")
    public Uni<Response> checkout() {
        return withTransaction(() -> Uni.combine().all()
                .unis(
                        productService.checkoutProduct(),
                        pocketService.updatePocket(),
                        shopService.updateStatus()
                ).asTuple())
                .map(objects -> ok(objects).build())
                .onFailure()
                .recoverWithItem((e) -> serverError().entity(e.getMessage()).build());
    }

}
