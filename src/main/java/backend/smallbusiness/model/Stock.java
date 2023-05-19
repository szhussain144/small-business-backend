package backend.smallbusiness.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stock extends BaseEntity{
    @ManyToOne
    Product product;
    @ManyToOne
    User user;
    int quantity;
}
