package backend.smallbusiness.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Product extends BaseEntity{
    String name;
    String description;
    Double price;
    String image;
    @ManyToOne
    Supplier supplier;
    int quantity;
}
