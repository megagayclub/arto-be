package com.arto.arto.global.config;

import com.arto.arto.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // 필터에 넣을 거 주입받기

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 세션 미사용 (JWT 사용)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // URL 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 1. 누구나 접속 가능 (회원가입, 로그인, 스웨거)
                        .requestMatchers(
                                "/api/v1/users",
                                "/api/v1/login",
                                "/api/v1/users/signup",
                                "/api/v1/users/reset-password-request",
                                "/api/v1/users/reset-password",
                                "/error"
                        ).permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        ).permitAll()

                        // 2. 작품 조회(GET)는 누구나 가능
                        .requestMatchers(HttpMethod.GET, "/api/v1/artworks/**").permitAll()
                        //찜하기 등록 어드민도 허용한 이유는 테스트할때 그냥 하려고 넣어둠
                        .requestMatchers("/api/v1/wishlists/**").hasAnyRole("USER", "ADMIN")

                        // 3. 작품 관리
                        .requestMatchers(HttpMethod.POST, "/api/v1/artworks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/artworks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/artworks/**").hasRole("ADMIN")


                        // 4. 나머지는 로그인만 하면 됨 (내 정보 수정, 탈퇴 등)
                        .anyRequest().authenticated()
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}