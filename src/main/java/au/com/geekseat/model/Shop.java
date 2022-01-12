package au.com.geekseat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class Shop extends BaseModel {
    private Integer invoiceNumber;
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("shop")
    private Product product;
}
