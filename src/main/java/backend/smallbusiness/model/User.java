package backend.smallbusiness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseEntity{
    String name;
    String address;
    String phoneNumber;
    String email;
    @JsonIgnore
    String password;
    UserType userType;
}
