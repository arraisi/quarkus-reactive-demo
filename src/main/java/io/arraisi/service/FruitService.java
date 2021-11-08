package io.arraisi.service;

import io.arraisi.model.Fruit;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FruitService implements PanacheRepository<Fruit> {
}
