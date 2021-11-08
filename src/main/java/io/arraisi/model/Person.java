package io.arraisi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.arraisi.helper.Utility;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Person extends PanacheEntity {
    public String name;
    public String address;
    public LocalDate birth;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "Person_Role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties("persons")
    public List<Role> roles = new ArrayList<>();

    @JsonIgnore
    public String mapData;

    @Transient
    public Map<String, Object> map = new HashMap<>();

    public static Person fromDecorator(PanacheEntityBase panacheEntityBase) {
        Person person = (Person) panacheEntityBase;
        if (Utility.isNotBlank(person.mapData)) {
            person.map = Utility.gson.fromJson(person.mapData, Utility.typeMapOfStringObject);
            person.mapData = null;
        }
        return person;
    }
}
