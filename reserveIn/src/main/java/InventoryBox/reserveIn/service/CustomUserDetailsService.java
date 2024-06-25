package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.CustomUserDetails;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("유저 없음"));
        return new CustomUserDetails(users);
    }
}
