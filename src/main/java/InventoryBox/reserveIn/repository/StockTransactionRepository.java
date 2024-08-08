package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.dto.StockTransactionDto;
import InventoryBox.reserveIn.entity.AllEnum.TransactionType;
import InventoryBox.reserveIn.entity.StockTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction,Long> {

    @Query("select new InventoryBox.reserveIn.dto.StockTransactionDto(st.id, a.id, a.name, st.transactionType,st.quantity, st.description, st.stock.id, b.username, st.createAt, st.updateAt) " +
            "from StockTransaction st " +
            "join st.product a " +
            "join st.users b")
    Page<StockTransactionDto> findStockTransactionWithProductName(Pageable pageable);
// 쿼리문을 object 배열로 객체를 저장
//    Page<StockTransaction> findByTransactionType(Pageable pageable, TransactionType transactionType);

}
