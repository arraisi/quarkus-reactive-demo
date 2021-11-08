package io.arraisi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fruit")
public class Fruit {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 40, unique = true)
    private String name;

}
