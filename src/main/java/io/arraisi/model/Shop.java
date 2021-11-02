package io.arraisi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Shop extends PanacheEntity {
    public String name;
    @OneToMany(mappedBy = "shop", cascade = ALL, fetch = EAGER)
    @JsonIgnoreProperties("shop")
    public List<Product> products = new ArrayList<>();
}
