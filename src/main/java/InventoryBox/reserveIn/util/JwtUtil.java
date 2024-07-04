package InventoryBox.reserveIn.util;

import InventoryBox.reserveIn.dto.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    //토큰 발급처만 확인하는 용도
    private final SecretKey secretKey;

    //SecretKey 객체 생성
    //양방향 대칭키 방식, 객체 형식으로 저장
    public JwtUtil(@Value(" ${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //검증한 token 에서 사용자명 반환
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    //검증한 token 에서 권한 반환
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    //token 만료 여부 확인
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }


    // token 유효기간 반환
//    public long getExpiration(String token) {
//        Date expiration = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
//        return expiration.getTime() - System.currentTimeMillis();
//    }

    // username, role, 만료시간을 받아 JWT 발급
    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
                //claim-> payload에 포함된 값
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) //토큰 발행시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //토큰 발행시간 + 토큰 유효시간
                .signWith(secretKey) //token 암포화 진행
                .compact();
    }
}

