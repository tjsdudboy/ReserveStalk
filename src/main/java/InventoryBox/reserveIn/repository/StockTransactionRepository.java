package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.entity.AllEnum.TransactionType;
import InventoryBox.reserveIn.entity.StockTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction,Long> {

    Page<StockTransaction> findByTransactionType(Pageable pageable, TransactionType transactionType);
}
