package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.UsersDto;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UsersDto findById(Long id) {
        Users users = userRepository.findById(id).orElseThrow();
        return UsersDto.toDto(users);
    }

    public UsersDto findByUsername(String username) {
        Users user = userRepository.findByUsername(username).orElseThrow();
        return UsersDto.toDto(user);
    }
}
