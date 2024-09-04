package InventoryBox.reserveIn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String staffName;
    private String staffNum;
    private String nickName;
    private String staffPhone;
    private String hireDate;
    private String StaffRole;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
