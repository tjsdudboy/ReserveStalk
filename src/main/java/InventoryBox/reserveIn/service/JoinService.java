package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.UsersDto;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersDto join(UsersDto usersDto) {
        Users user = new Users();
        if(userRepository.existsByUsername(usersDto.getUsername())){
            throw new IllegalArgumentException("이미 존재하는 유저 아이디");
        }
        if(!usersDto.getPassword().equals(usersDto.getCheckPassword())){
            throw new BadCredentialsException("비밀번호 일치하지 않음");
        }
        user.setUsername(usersDto.getUsername());
        user.setName(usersDto.getName());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        user.setUsername(usersDto.getUsername());
        user.setEmail(usersDto.getEmail());
        user.setRole("USER");

        user = userRepository.save(user);
        return UsersDto.toDto(user);
    }

    //ToDo 이미지 저장
}
