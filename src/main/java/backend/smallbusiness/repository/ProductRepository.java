package backend.smallbusiness.repository;

import backend.smallbusiness.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p where p.isDeletedRecord = false ")
    List<Product> findAll();
    @Query("SELECT p FROM Product p where p.isDeletedRecord = false AND p.supplier.id = ?1")
    List<Product> findAllProductsBySupplier(Long id);

}
