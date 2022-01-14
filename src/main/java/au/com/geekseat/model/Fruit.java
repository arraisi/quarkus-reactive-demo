package au.com.geekseat.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fruit")
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

    public Fruit(String name) {
        this.name = name;
    }

    public Fruit() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
