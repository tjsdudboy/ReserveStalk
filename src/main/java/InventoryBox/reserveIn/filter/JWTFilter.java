package InventoryBox.reserveIn.filter;

import InventoryBox.reserveIn.dto.CustomUserDetails;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//토큰 검증 필터 , OncePerRequestFilter -> 요청에 대해 한 번만 동작하는 필터
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //요청에서 Authorization 키값을 가져옴
        String authorization = request.getHeader("Authorization");

        System.out.println("Authorization Header: " + authorization);


        //Authorization 토큰이 null이거나 Bearer로 시작하는 것이 아닌것은 doFilter 를 통해 필터를 종료하고 다음 필터로 전달
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 소멸시간 검증, 종료된 토큰은 doFilter 를 통해 필터를 종료하고 다음 필터로 전달
        String token = authorization.split(" ")[1];
        System.out.println("Extracted Token: " + token);

        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //해당 토큰에서 username, role 값을 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        System.out.println("Username: " + username);
        System.out.println("Role: " + role);

        //user 값 초기화
        Users users = new Users();
        users.setUsername(username);
        users.setPassword("tempPassword");
        users.setRole(role);

        //userDetails 에 회원정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(users);

        //인증토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response); //다음 필터에 전달
    }
}
