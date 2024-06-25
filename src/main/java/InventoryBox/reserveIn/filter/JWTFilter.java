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

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null && !authorization.startsWith("Bearer")){
            System.out.println("tocken null");
            filterChain.doFilter(request,response);
        }
        System.out.println("authorization now");
        String tocken = authorization.split("")[1];
        if (jwtUtil.isExpired(tocken)) {
            System.out.println("tocken exprired");
            filterChain.doFilter(request, response);
        }

        Users users = new Users();
        users.setUsername(jwtUtil.getUsername(tocken));
        users.setPassword("temp");
        users.setPassword(jwtUtil.getRole(tocken));

        CustomUserDetails customUserDetails = new CustomUserDetails(users);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
