package InventoryBox.reserveIn.util;

import InventoryBox.reserveIn.entity.login.Refresh;
import InventoryBox.reserveIn.repository.RefreshRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public void createTokenAndSetResponse(HttpServletResponse response, String username, String role) {
        String ACToken = jwtUtil.createJwt("access", username,role,60*10*1000L);
        String RFToken = jwtUtil.createJwt("refresh", username,role, 24*60*60*1000L);

        System.out.println("리프레시 토큰 발급: " + RFToken);

        //응답
        response.setHeader("access", ACToken);
        response.addCookie(createCookie("refresh", RFToken));
        response.setStatus(HttpStatus.OK.value());    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true); // https에서만 전송
        cookie.setPath("/"); // 쿠키 적용 범위 설정
        cookie.setHttpOnly(true); // 클라이언트의 자바스크립트 접근 방지
        return cookie;
    }

    public void addRefresh(String username, String refresh, Long expiredMS) {
        Date date = new Date(System.currentTimeMillis() + expiredMS);
        Refresh refreshEntity = new Refresh();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpriration(date.toString());
        refreshRepository.save(refreshEntity);
    }
}