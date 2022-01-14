package au.com.geekseat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "shop")
public class Shop extends BaseModel {
    private Integer invoiceNumber;
    private Integer quantity;
    private Boolean active = true;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("shop")
    private Product product;

    public Shop(Integer invoiceNumber, Integer quantity, Boolean active, Product product) {
        this.invoiceNumber = invoiceNumber;
        this.quantity = quantity;
        this.active = active;
        this.product = product;
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
}
