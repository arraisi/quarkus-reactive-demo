package au.com.geekseat.service;

import au.com.geekseat.model.Product;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductService implements PanacheRepository<Product> {
    public Uni<List<Product>> checkoutProduct() {
        return findAll().list()
                .map(products -> {
                    products.forEach(product -> {
                        if (product.getQuantity() < 1) {
                            throw new RuntimeException(product.getName() + " is out of stock");
                        }
                        product.setQuantity(product.getQuantity() - 1);
                        persist(product);
                    });
                    return products;
                });
    }
}
