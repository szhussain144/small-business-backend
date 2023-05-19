package backend.smallbusiness.repository;

import backend.smallbusiness.model.Product;
import backend.smallbusiness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u where u.email = ?1")
    Optional<User> loginUser(String email);

    @Query("SELECT s.product FROM Stock s where s.user = ?1 and s.product.isDeletedRecord = false ")
    List<Product> findUserProduct(User user);

    Optional<User> findByPhoneNumber(String phoneNumber);

}
