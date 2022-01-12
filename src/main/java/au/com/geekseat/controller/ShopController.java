package au.com.geekseat.controller;

import au.com.geekseat.model.Shop;
import au.com.geekseat.service.PocketService;
import au.com.geekseat.service.ProductService;
import au.com.geekseat.service.ShopService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("/checkout/product")
    public Uni<Response> checkout() {
        return Panache.withTransaction(() -> productService.checkoutProduct())
                .map(created -> Response.ok(created).build());
    }

    @PUT
    @Path("/checkout")
    public Uni<Response> checkouts() {
        return Panache.withTransaction(() -> Uni.combine().all()
                        .unis(
                                productService.checkoutProduct(),
                                pocketService.updatePocket()
                        ).asTuple())
                .map(created -> Response.ok(created).build())
                .onFailure()
                .recoverWithItem((e) -> Response.serverError().build());
    }

}
