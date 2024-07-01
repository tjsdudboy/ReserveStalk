package InventoryBox.reserveIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;//userId
    private String name;
    private String password;
    private String email;
    private String role;

    @OneToMany(mappedBy = "users")
    private List<Product> products;

    @OneToMany(mappedBy = "users")
    private List<WorkList> workLists;

    @OneToMany(mappedBy = "users")
    private List<StockTransaction> stockTransactions;

//    @OneToMany(mappedBy = "stock")
//    private List<Stock>

    @OneToMany(mappedBy = "users")
    private List<Reservation> reservation;



}
