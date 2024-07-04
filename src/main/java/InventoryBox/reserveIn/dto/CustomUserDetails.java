package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Users users;

    //사용자 권한 반환, Role 값을 문자열로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return users.getRole();
            }
        });
        return collection;
    }

    //계정 만료여부 확인 -> 만료되지 않은 경우 true 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 만료여부 확인 -> 잠겨있지 않은 경우 true 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //인증정보(비밀번호) 만료여부 확인 -> 만료되지 않은 경우 true 반환
    //비밀번호 주기적으로 변경하도록 요구하는 시스템에 사용
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정활성화 여부 확인 -> 활성화된 경우 true 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    //비밀번호 반환
    @Override
    public String getPassword() {
        return users.getPassword();
    }

    //Id반환
    @Override
    public String getUsername() {
        return users.getUsername();
    }
}
