package io.arraisi.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.MultiCollect;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Slf4j
@Entity
@Cacheable
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

    public Fruit(Long id, String name) {
        super.id = id;
        this.name = name;
    }

    public Fruit() {
    }

    public static Multi<Fruit> findAll(MySQLPool client) {
        StringBuilder query = new StringBuilder("SELECT id, name FROM Fruit ORDER BY name ASC");
        return client.query(query.toString()).execute()
                // Create a Multi from the set of rows:
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                // For each row create a fruit instance
                .onItem().transform(Fruit::from);
    }

    public static Uni<List<Fruit>> list(MySQLPool client) {
        StringBuilder query = new StringBuilder("SELECT id, name FROM Fruit ORDER BY name ASC");
        return client.query(query.toString()).execute()
                .onItem().transformToMulti(set -> {
                    log.info("list set: {}", set);
                    log.info("list set.value: {}", set.iterator());
                    Multi<Row> iterable = Multi.createFrom().iterable(set);
                    Multi<Fruit> transform = iterable.onItem().transform(Fruit::from);
                    Uni<List<Fruit>> listUni = transform.collect().asList();
//                    listUni.convert().toPublisher().subscribe();
                    return Multi.createFrom().iterable(set);
                })
                .onItem().transform(Fruit::from).collect().asList();
    }

    public static Uni<Fruit> findById(MySQLPool client, Long id) {
        return client.preparedQuery("SELECT id, name FROM Fruit WHERE id = ?")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Long> save(MySQLPool client, Fruit fruit) {
        return client.preparedQuery("INSERT INTO Fruit(name) VALUES (?) returning (id)")
                .execute(Tuple.of(name))
                .onItem().transform(rowSet -> rowSet.iterator().next().getLong("id"));
    }

    public static Uni<Boolean> update(MySQLPool client, Fruit fruit) {
        return client.preparedQuery("UPDATE Fruit SET name = ? WHERE id = ?").execute(Tuple.of(fruit.name, fruit.id))
                .onItem().transform(rowSet -> rowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(MySQLPool client, Long id) {
        return client.preparedQuery("DELETE FROM Fruit WHERE id = ?").execute(Tuple.of(id))
                .onItem().transform(rowSet -> rowSet.rowCount() == 1);
    }

    private static Fruit from(Row row) {
        return new Fruit(row.getLong("id"), row.getString("name"));
    }
}
