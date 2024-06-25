package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.Product;
import InventoryBox.reserveIn.entity.category.Category1;
import InventoryBox.reserveIn.entity.category.Category2;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int unitPrice;
    private Category1 category1;
    private Category2 category2;
    private LocalDateTime date;
    private String userId;

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .unitPrice(product.getUnitPrice())
                .category1(product.getCategory1())
                .category2(product.getCategory2())
                .date(product.getCreateAt())
                .userId(product.getUsers().getUserId())
                .build();
    }
}
