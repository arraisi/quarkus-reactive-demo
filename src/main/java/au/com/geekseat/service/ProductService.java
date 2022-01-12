package au.com.geekseat.service;

import au.com.geekseat.model.Product;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService implements PanacheRepository<Product> {
}
