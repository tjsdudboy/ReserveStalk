package InventoryBox.reserveIn.controller;

import InventoryBox.reserveIn.dto.UsersDto;
import InventoryBox.reserveIn.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    // 마이페이지 수정 전 비밀번호 확인
    @PostMapping("/checkPassword")
    public ResponseEntity<String> verifyPassword(@RequestBody UsersDto usersDto){
        boolean check = myPageService.checkPassword(usersDto);
        if(check){
            return ResponseEntity.ok("비밀번호 일치");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 일치하지 않음");
        }
    }

    // 마이페이지 기본 정보 조회
    @GetMapping("/MyPage/{userId}")
    public ResponseEntity<UsersDto> findByUser(@PathVariable String userId){
        UsersDto user = myPageService.findByUsersById(userId);
        return ResponseEntity.ok(user);
    }

    // 마이페이지 수정
    @PutMapping("/MyPage/{userId}")
    public ResponseEntity<UsersDto> updateUser(@RequestBody UsersDto usersDto,
                                               @PathVariable String userId) {
        UsersDto user = myPageService.updateUser(usersDto, userId);
        return ResponseEntity.ok(user);
    }
}
