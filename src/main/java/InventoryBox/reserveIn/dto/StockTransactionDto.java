package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.AllEnum.TransactionType;
import InventoryBox.reserveIn.entity.StockTransaction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockTransactionDto {

    private Long id;
    private Long productId;
    private TransactionType InOutType;
    private int quantity;
    private String description;
    private Long stockId;
    private String user_name; //유저이름

    public static StockTransactionDto toDto(StockTransaction stockTransaction) {
        return StockTransactionDto.builder()
                .id(stockTransaction.getId())
                .productId(stockTransaction.getProduct().getId())
                .InOutType(stockTransaction.getTransactionType())
                .quantity(stockTransaction.getQuantity())
                .description(stockTransaction.getDescription())
                .stockId(stockTransaction.getStock().getId())
                .user_name(stockTransaction.getUsers().getName())
                .build();
    }
}
