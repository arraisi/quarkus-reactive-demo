package io.arraisi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Role extends PanacheEntity {
    public String name;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "Person_Role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @JsonIgnoreProperties("roles")
    public List<Person> persons = new ArrayList<>();

    public static Uni<List<Role>> findByPersonId(Long personId){
        return list("person", personId);
    }
}
