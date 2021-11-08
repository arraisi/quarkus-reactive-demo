package io.arraisi.service;

import io.arraisi.model.Shop;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShopService implements PanacheRepository<Shop> {
}
