package InventoryBox.reserveIn.entity;

import InventoryBox.reserveIn.entity.AllEnum.MemberCat;
import InventoryBox.reserveIn.entity.AllEnum.MemberRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String memberId;
    private String memberName;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    private MemberCat memberCat;
    private String phoneNum;
    private int totalSales;
    private int numberOfSales;
    private int prepaidAmount; //잔액
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
