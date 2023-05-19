package backend.smallbusiness.dto.response;

import backend.smallbusiness.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    String jwt;
    User user;
}
