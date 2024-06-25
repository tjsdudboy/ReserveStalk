package InventoryBox.reserveIn.entity;

import InventoryBox.reserveIn.entity.category.Category1;
import InventoryBox.reserveIn.entity.category.Category2;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String description;

    private int price;

    private int unitPrice;

    @Enumerated(EnumType.STRING)
    private Category1 category1;

    @Enumerated(EnumType.STRING)
    private Category2 category2;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
