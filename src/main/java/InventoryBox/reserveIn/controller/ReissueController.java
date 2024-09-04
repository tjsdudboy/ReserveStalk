package InventoryBox.reserveIn.controller;

import InventoryBox.reserveIn.repository.RefreshRepository;
import InventoryBox.reserveIn.util.JwtUtil;
import InventoryBox.reserveIn.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
//Accesstoken 이 만료될 경우 Refreshtoken 생성 요청
public class ReissueController {

    private final JwtUtil jwtUtil;
    private final TokenUtil tokenUtil;
    private final RefreshRepository refreshRepository;

    @Autowired
    public ReissueController(JwtUtil jwtUtil, TokenUtil tokenUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenUtil = tokenUtil;
        this.refreshRepository = refreshRepository;
    }


    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //refreshtoken 가져오기
        String rftoken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                rftoken = cookie.getValue();
            }
        }
        //refreshtoken이 null인 경우 BadRequest 상태값 전송
        if (rftoken == null) {
            return new ResponseEntity<>("refreshtoken null", HttpStatus.BAD_REQUEST);
        }

        //refreshtoken이 만료된 경우 예외 발생
        try {
            jwtUtil.isExpired(rftoken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        //카테고리가 refreshtoken이 아닌 것은 BadRequest 상태값 전송
        String category = jwtUtil.getTokenCT(rftoken);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("refresh token 없음", HttpStatus.BAD_REQUEST);
        }

        // DB에 RefreshToken이 저장되어 있는 지 확인
        Boolean isExist = refreshRepository.existsByRefresh(rftoken);
        if (!isExist) {
            return new ResponseEntity<>("refresh toekn 없음", HttpStatus.BAD_REQUEST);
        }

        //username, role 값을 가져와서 토큰 생성, ok 응답 전송
        String username = jwtUtil.getUsername(rftoken);
        String role = jwtUtil.getRole(rftoken);

        refreshRepository.deleteAllByRefresh(rftoken);
        tokenUtil.addRefresh(username,rftoken,24*60*60*1000L);

        tokenUtil.createTokenAndSetResponse(response,username,role);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
