package au.com.geekseat.service;

import au.com.geekseat.model.Shop;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.vertx.mutiny.mysqlclient.MySQLPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShopService implements PanacheRepository<Shop> {
    @Inject
    MySQLPool client;

}
