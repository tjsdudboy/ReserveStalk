package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.dto.StockDto;
import InventoryBox.reserveIn.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s.id, p.name, p.category1, p.category2, p.price, p.unitPrice, s.currentStock " +
            "FROM Stock s " +
            "JOIN s.product p")
    Page<Object[]> findStockWithProductDetails(Pageable pageable);
}
