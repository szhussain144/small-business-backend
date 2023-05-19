package backend.smallbusiness.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Purchase extends BaseEntity{
    @ManyToOne
    Product product;
    @ManyToOne
    Supplier supplier;
    LocalDateTime purchaseDate;
    int quantity;
}
