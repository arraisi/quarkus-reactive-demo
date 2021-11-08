package io.arraisi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class Shop extends BaseModel {
    private String name;
    @OneToMany(mappedBy = "shop", cascade = ALL, fetch = EAGER)
    @JsonIgnoreProperties("shop")
    private List<Product> products = new ArrayList<>();
}
