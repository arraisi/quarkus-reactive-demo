package io.arraisi.service;

import io.arraisi.model.Product;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService implements PanacheRepository<Product> {
}
