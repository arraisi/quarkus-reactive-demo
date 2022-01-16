package au.com.geekseat.model;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "shop")
public class Shop extends BaseModel {
    private Integer invoiceNumber;
    private Integer quantity;
    private Boolean active = true;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Shop(Integer invoiceNumber, Integer quantity, Boolean active, Product product, Person person) {
        this.invoiceNumber = invoiceNumber;
        this.quantity = quantity;
        this.active = active;
        this.product = product;
        this.person = person;
    }

    public Shop() {
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
