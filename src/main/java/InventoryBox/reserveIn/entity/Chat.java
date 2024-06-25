package InventoryBox.reserveIn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;
    private String message;
    private LocalDateTime sentAt;

}
