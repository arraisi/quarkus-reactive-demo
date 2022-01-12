package au.com.geekseat.service;

import au.com.geekseat.model.Product;
import au.com.geekseat.model.Shop;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProductService implements PanacheRepository<Product> {
    @Inject
    ShopService shopService;

    public Uni<Tuple2<List<Product>, List<Shop>>> checkoutProduct() {
        return Panache.withTransaction(() -> Uni.combine().all()
                .unis(
                        checkout(),
                        shopService.updateStatus()
                ).asTuple());
    }

    private Uni<List<Product>> checkout() {
        return findAll().list()
                .map(products -> {
                    products.forEach(product -> {
                        if (product.getQuantity() >= 2) {
                            product.setQuantity(product.getQuantity() - 2);
                        }
                        persist(product);
                    });
                    return products;
                });
    }
}
