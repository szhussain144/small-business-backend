package backend.smallbusiness.repository;

import backend.smallbusiness.model.Invoice;
import backend.smallbusiness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT i FROM Invoice i where i.customer = ?1")
    List<Invoice> findAllById(User user);
    @Query("SELECT i FROM Invoice i where i.customer = ?1 AND i.supplier = ?2")
    List<Invoice> findInvoicesById(User user,Long id);
}
