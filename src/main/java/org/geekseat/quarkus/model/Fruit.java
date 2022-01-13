package org.geekseat.quarkus.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fruit")
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

}
