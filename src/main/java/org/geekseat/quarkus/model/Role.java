package org.geekseat.quarkus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "person_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @JsonIgnoreProperties("roles")
    private List<Person> persons = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
