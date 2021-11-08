package io.arraisi.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;

@Slf4j
@Entity
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

}
