package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.AllEnum.TransactionType;
import InventoryBox.reserveIn.entity.StockTransaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockTransactionDto {

    private Long id;
    private Long productId;
    private String productName;
    private TransactionType transactionType;
    private int quantity;
    private String description;
    private Long stockId;
    private String username; //유저Id
    private LocalDateTime creatDate;
    private LocalDateTime updateDate;

    @Builder
    public StockTransactionDto(Long id, Long productId, String productName, TransactionType transactionType, int quantity, String description, Long stockId, String username,LocalDateTime creatDate, LocalDateTime updateDate) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.description = description;
        this.stockId = stockId;
        this.username = username;
        this.creatDate = creatDate;
        this.updateDate = updateDate;
    }

    public static StockTransactionDto toDto(StockTransaction stockTransaction) {
        return StockTransactionDto.builder()
                .id(stockTransaction.getId())
                .productId(stockTransaction.getProduct().getId())
                .productName(stockTransaction.getProduct().getName())
                .transactionType(stockTransaction.getTransactionType())
                .quantity(stockTransaction.getQuantity())
                .description(stockTransaction.getDescription())
                .stockId(stockTransaction.getStock().getId())
                .username(stockTransaction.getUsers().getUsername())
                .creatDate(stockTransaction.getCreateAt())
                .updateDate(stockTransaction.getUpdateAt())
                .build();
    }
}

