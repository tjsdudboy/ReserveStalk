package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.UsersDto;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //ToDo ID별 유저 조회
    public UsersDto findByUsersById(String userId) {
        return userRepository.findByUserId(userId).map(UsersDto::toDto).orElseThrow(
                ()-> new IllegalArgumentException("유저 없음"));
    }

    //TODo 유저 정보 수정
    @Transactional
    public UsersDto updateUser(UsersDto usersDto, String userId) {
        Users users = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("유저 없음"));
        // 신규 입력한 비밀번호 재확인(password && checkPassword)
        if(!usersDto.getPassword().equals(usersDto.getCheckPassword())){
            throw new BadCredentialsException("비밀번호 일치하지 않음");
        }
        users.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        users.setUsername(usersDto.getUsername());
        return UsersDto.toDto(users);
    }

    //ToDo 마이페이지 수정 전 기존 비밀번호 확인
    public boolean checkPassword(UsersDto usersDto) {
        Users users = userRepository.findByUserId(usersDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("유저 없음"));
        return passwordEncoder.matches(usersDto.getOriginPassword(), users.getPassword());

    }

}
