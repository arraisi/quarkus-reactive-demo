package au.com.geekseat.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@Table(name = "fruit")
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

}
