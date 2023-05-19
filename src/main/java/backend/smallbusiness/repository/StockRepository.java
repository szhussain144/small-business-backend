package backend.smallbusiness.repository;

import backend.smallbusiness.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
