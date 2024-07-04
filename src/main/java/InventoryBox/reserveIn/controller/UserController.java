package InventoryBox.reserveIn.controller;

import InventoryBox.reserveIn.dto.UsersDto;
import InventoryBox.reserveIn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<UsersDto> findById(@PathVariable Long id) {
        UsersDto usersDto = userService.findById(id);
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/user")
    public ResponseEntity<UsersDto> findByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        UsersDto usersDto = userService.findByUsername(username);
        System.out.println("성공???");
        return ResponseEntity.ok(usersDto);
    }
}
