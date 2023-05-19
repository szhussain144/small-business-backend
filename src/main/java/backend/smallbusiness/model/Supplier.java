package backend.smallbusiness.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Supplier extends BaseEntity{
    String name;
    String address;
    String phoneNumber;
    String email;
}
