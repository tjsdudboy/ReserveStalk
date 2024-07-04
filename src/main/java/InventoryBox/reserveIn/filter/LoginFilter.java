package InventoryBox.reserveIn.filter;

import InventoryBox.reserveIn.dto.CustomUserDetails;
import InventoryBox.reserveIn.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@AllArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //securityConfig에서 formLogin 방식을 disable 하였기에 LoginFilter에서 구현

    // Client가 Server 로그인 정보를 Post할 때 UsernamePasswordAuthenticationFilter가 로그인 정보를 가로챔
    // -> 토큰을 생성 후 아이디/비번을 담아 매니저에 전달

    //ID,PW로 인증을 시도할 때 호출, 인증 처리 위임 로직
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        //클라이언트 요청에서 username, password 가로챔
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        //usernamePasswordAuthenticationFilter 에서 AuthenticationManager 에게 전달 할때 DTO 와 같은 형태로 정보를 전달
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, null); //null 값은 role 같은 값 적용
        //검증을 위한 AuthenticationManager 로 전달 및 검증
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    //로그인 인증 성공시 실행, JWT 토큰 발급(JwtUtil Class)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //username 추출
        String username = customUserDetails.getUsername();

        //Role 값 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username,role,3600000L);
        response.setHeader("Authorization", "Bearer " + token); //Authorization 키값을 담음, value 는 토큰

        System.out.println("로그인 성공");
    }

    //인증 실패시 호출, 실패 사유에 따른 예외 처리 및 응답 값 제어
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        System.out.println("로그인 실패");
    }
}
