package io.arraisi.service;

import io.arraisi.helper.Decorator;
import io.arraisi.model.Person;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PersonService extends BaseService implements PanacheRepository<Person> {

    @Inject
    MySQLPool client;

    public static final Decorator<Person> toDecorator = new Decorator<Person>() {
        public Person decorate(Person entity) {
            if (entity != null) {
                toDecorate(entity);
            }
            return entity;
        }
    };

    public static final Decorator<Person> fromDecorator = new Decorator<Person>() {
        public Person decorate(Person entity) {
            if (entity != null) {
                fromDecorate(entity);
            }
            return entity;
        }
    };

    public Uni<Long> rowCount() {
        return client.query("SELECT count(*) as rowCount FROM Person").execute()
                .map(RowSet::iterator)
                .map(row -> row.next().getLong("rowCount"));
    }

    public Uni<Boolean> update(Person person) {
        return client.preparedQuery("UPDATE Person SET name = ?, birth = ?, mapData = ? WHERE id = ?")
                .execute(Tuple.of(person.getName(), person.getBirth(), person.getMapData(), person.getId()))
                .onItem().transform(rowSet -> rowSet.rowCount() == 1);
    }

    public Uni<Boolean> queryDelete(Long id) {
        return client.preparedQuery("DELETE FROM Fruit WHERE id = ?").execute(Tuple.of(id))
                .onItem().transform(rowSet -> rowSet.rowCount() == 1);
    }

    public Uni<Person> queryFindById(Long id) {
        return client.preparedQuery("select id, active, birth, email, mapData, name, password from Person where id = ?")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Multi<Person> queryPersonMultiList() {
        return client.query("SELECT id, active, birth, email, mapData, name, password FROM Person ORDER BY name ASC").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(PersonService::from);
    }

    public Uni<List<Person>> queryPersonUniList() {
        return client.query("SELECT id, active, birth, email, mapData, name, password FROM Person ORDER BY name ASC").execute()
                .onItem().transformToMulti(rows -> rows.iterator().toMulti())
                .onItem().transform(PersonService::from).collect().asList();
    }

    private static Person from(Row row) {
        Person person = new Person();
        person.setId(row.getLong("id"));
        person.setName(row.getString("name"));
        person.setBirth(row.getLocalDate("birth"));
        person.setEmail(row.getString("email"));
        person.setPassword(row.getString("password"));
        person.setActive(row.getBoolean("active"));
        return person;
    }
}
