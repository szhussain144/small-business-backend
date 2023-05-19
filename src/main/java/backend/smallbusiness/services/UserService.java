package backend.smallbusiness.services;

import backend.smallbusiness.dto.response.LoginResponse;
import backend.smallbusiness.model.Product;
import backend.smallbusiness.model.User;
import backend.smallbusiness.repository.UserRepository;
import backend.smallbusiness.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public LoginResponse login(LoginDTO loginDTO){
        Optional<User> user = userRepository.loginUser(loginDTO.getEmail());
        if(user.isEmpty()){
            return null;
        }
        if(passwordEncoder.matches(loginDTO.getPassword(),user.get().getPassword())) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(jwtService.generateToken(user.get()));
            loginResponse.setUser(user.get());
            return loginResponse;
        }else{
            return null;
        }
    }

    public List<Product> getUserProduct(User user){
        try{
            List<Product> products = userRepository.findUserProduct(user);
            return products;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
