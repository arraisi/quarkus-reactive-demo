package au.com.geekseat.service;

import au.com.geekseat.helper.Decorator;
import au.com.geekseat.model.Shop;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static au.com.geekseat.service.BaseService.fromDecorate;
import static au.com.geekseat.service.BaseService.toDecorate;

@ApplicationScoped
public class ShopService implements PanacheRepository<Shop> {
    public static final Decorator<Shop> toDecorator = new Decorator<Shop>() {
        public Shop decorate(Shop entity) {
            if (entity != null) {
                toDecorate(entity);
            }
            return entity;
        }
    };

    public static final Decorator<Shop> fromDecorator = new Decorator<Shop>() {
        public Shop decorate(Shop entity) {
            if (entity != null) {
                fromDecorate(entity);
            }
            return entity;
        }
    };

    public Uni<List<Shop>> updateStatus() {
        return findAll().list()
                .map(shops -> {
                    shops.forEach(shop -> {
                        if (!shop.getActive()) {
                            throw new RuntimeException("Wrong invoice");
                        }
                        shop.setActive(false);
                        persist(shop);
                    });
                    return shops;
                });
    }
}
