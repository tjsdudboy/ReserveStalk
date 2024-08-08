package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.Product;
import InventoryBox.reserveIn.entity.Stock;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDto {
    private Long id;
    private String productName;
    private String cat1;
    private String cat2;
    private int price;
    private int unitPrice;
    private int currentStock;

    public static StockDto toDto(Stock stock) {
        return StockDto.builder()
                .id(stock.getId())
                .productName(stock.getProduct().getName())
                .cat1(String.valueOf(stock.getProduct().getCategory1()))
                .cat2(String.valueOf(stock.getProduct().getCategory2()))
                .price(stock.getProduct().getPrice())
                .unitPrice(stock.getProduct().getUnitPrice())
                .currentStock(stock.getCurrentStock())
                .build();
    }
}
