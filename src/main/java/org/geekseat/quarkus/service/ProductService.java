package org.geekseat.quarkus.service;

import org.geekseat.quarkus.model.Product;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService implements PanacheRepository<Product> {
}
