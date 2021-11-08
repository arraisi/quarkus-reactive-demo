package io.arraisi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.arraisi.helper.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate birth;
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    private Boolean active = true;
    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "Person_Role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties("persons")
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    private String mapData;

    @Transient
    private Map<String, Object> map = new HashMap<>();

    public static Person toDecorator(Person person) {
        if (!person.map.isEmpty()) {
            person.mapData = Utility.gson.toJson(person.map);
        }
        return person;
    }

    public static Person fromDecorator(Person person) {
        if (Utility.isNotBlank(person.mapData)) {
            person.map = Utility.gson.fromJson(person.mapData, Utility.typeMapOfStringObject);
            person.mapData = null;
        }
        return person;
    }
}
