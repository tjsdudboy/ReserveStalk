package InventoryBox.reserveIn.api;

import InventoryBox.reserveIn.filter.JWTFilter;
import InventoryBox.reserveIn.filter.LoginFilter;
import InventoryBox.reserveIn.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .formLogin(config-> config.disable())
                .csrf(config-> config.disable())
                .httpBasic(config-> config.disable());
        http
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/join")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/ws/**")).permitAll()
                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
