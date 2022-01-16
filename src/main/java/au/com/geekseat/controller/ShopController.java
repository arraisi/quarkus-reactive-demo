package au.com.geekseat.controller;

import au.com.geekseat.model.Shop;
import au.com.geekseat.service.PocketService;
import au.com.geekseat.service.ProductService;
import au.com.geekseat.service.ShopService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

import static au.com.geekseat.service.ShopService.toDecorator;
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

    @POST
    public Uni<Response> save(Shop shop) {
        shop.createdBy();
        return Panache.withTransaction(() -> shopService.persist(toDecorator.decorate(shop)))
                .map(response -> created(URI.create("/person" + response.getId())).build());
    }

    @GET
    @Path("/list")
    public Uni<List<Shop>> list() {
        return shopService.listAll(Sort.by("id"));
    }

}
