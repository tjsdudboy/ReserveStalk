package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersDto {
    private Long id;
    private String userId;
    private String username;
    private String name;
    private String password;
    private String checkPassword;
    private String originPassword;
    private String email;
    private String role;


    public static UsersDto toDto(Users users) {
        return UsersDto.builder()
                .id(users.getId())
                .userId(users.getUserId())
                .name(users.getName())
                .username(users.getUsername())
                .email(users.getEmail())
                .role(users.getRole())
                .build();
    }
}