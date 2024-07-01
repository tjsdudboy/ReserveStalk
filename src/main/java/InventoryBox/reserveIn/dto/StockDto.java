package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.Product;
import InventoryBox.reserveIn.entity.Stock;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDto {
    private Long id;
    private Long productId;
    private int currentStock;

    public static StockDto toDto(Stock stock) {
        return StockDto.builder()
                .id(stock.getId())
                .productId(stock.getProduct().getId())
                .currentStock(stock.getCurrentStock())
                .build();
    }
}
