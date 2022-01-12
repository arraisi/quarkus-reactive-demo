package au.com.geekseat.service;

import au.com.geekseat.model.Shop;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ShopService implements PanacheRepository<Shop> {
    public Uni<List<Shop>> updateStatus() {
        return findAll().list()
                .map(shops -> {
                    shops.forEach(shop -> {
                        /* ON FAILURE TEST */
//                        if (shop.getId() == 3) {
//                            throw new RuntimeException();
//                        }
                        shop.setActive(false);
                        persist(shop);
                    });
                    return shops;
                });
    }
}
