package InventoryBox.reserveIn.controller;

import InventoryBox.reserveIn.dto.UsersDto;
import InventoryBox.reserveIn.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JoinController {

    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UsersDto usersDto) {
        joinService.join(usersDto);
        return ResponseEntity.ok("회원가입");
    }
}
