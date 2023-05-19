package backend.smallbusiness.repository;

import backend.smallbusiness.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query("SELECT s FROM Supplier s where s.isDeletedRecord = false")
    List<Supplier> findAll();
}
