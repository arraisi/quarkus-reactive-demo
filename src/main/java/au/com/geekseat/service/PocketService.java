package au.com.geekseat.service;

import au.com.geekseat.model.Pocket;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class PocketService implements PanacheRepository<Pocket> {
    @Inject
    MySQLPool client;

    public Uni<Pocket> updatePocket() {
        return findById(1L)
                .map(pocket -> {
                    pocket.setBalance(pocket.getBalance().subtract(new BigDecimal(100)));
//                    throw new RuntimeException();
                    return pocket;
                });
    }
}
