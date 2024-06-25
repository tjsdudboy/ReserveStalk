package InventoryBox.reserveIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private String userId;
    private String username;
    private String name;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private String role;
}
