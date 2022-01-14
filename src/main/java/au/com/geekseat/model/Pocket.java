package au.com.geekseat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pocket")
public class Pocket {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal balance;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnoreProperties("pockets")
    private Person person;

    public Pocket(Long id, String name, BigDecimal balance, Person person) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.person = person;
    }

    public Pocket() {
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
