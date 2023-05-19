package backend.smallbusiness.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Invoice extends BaseEntity{
    LocalDateTime invoiceDate;
    double amount;
    @ManyToOne
    User customer;
    @ManyToOne
    Supplier supplier;
    @ManyToOne
    Product product;
}
