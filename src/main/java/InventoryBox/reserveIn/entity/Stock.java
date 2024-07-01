package InventoryBox.reserveIn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users users;
    private int currentStock;


    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<StockTransaction> stockTransaction = new ArrayList<>();

}
