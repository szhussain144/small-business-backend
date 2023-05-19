package backend.smallbusiness.controller;

import backend.smallbusiness.dto.LoginDTO;
import backend.smallbusiness.dto.response.LoginResponse;
import backend.smallbusiness.model.Product;
import backend.smallbusiness.model.User;
import backend.smallbusiness.services.ApiResponse;
import backend.smallbusiness.services.JwtService;
import backend.smallbusiness.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "user")
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginDTO loginDTO){
        System.out.println(passwordEncoder.encode(loginDTO.getPassword()));
        LoginResponse loginResponse = userService.login(loginDTO);
        if(loginResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(loginResponse,"Login successful"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Fail to login"));
        }
    }

    @GetMapping(value = "products")
    public ResponseEntity<ApiResponse<List<Product>>> getUserProduct(HttpServletRequest httpServletRequest){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Product> products = userService.getUserProduct(user);
        if(products.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(products,"Products fetched"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Failed to fetch products"));
        }
    }
}
