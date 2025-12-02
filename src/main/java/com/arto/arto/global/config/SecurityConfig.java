package com.arto.arto.global.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 비밀번호 암호화 빈 등록 (BCrypt)
    // DB에 비밀번호를 저장할 때 평문으로 저장하면 안 되므로, 이 빈을 사용해 암호화합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. AuthenticationManager 빈 등록
    // 로그인 시 "아이디/비밀번호가 맞는지 검사"하는 역할을 수행하는 관리자입니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 3. Security Filter Chain 설정 (핵심 보안 규칙)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // (1) CSRF 보호 비활성화
                // 우리는 세션이 아닌 JWT를 사용할 것이므로 CSRF 토큰이 필요 없습니다.
                .csrf(AbstractHttpConfigurer::disable)

                // (2) Form 로그인 & Http Basic 인증 비활성화
                // 프론트엔드(React)와 API 통신을 할 것이므로 기본 로그인 폼은 끕니다.
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // (3) 세션 설정: STATELESS
                // 중요! 서버에 세션을 만들지 않고, 매 요청마다 토큰을 확인할 것입니다.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // (4) URL별 권한 설정 (인가)
                .authorizeHttpRequests(auth -> auth
                        // 1. 회원가입, 로그인은 누구나 가능 (POST)
                        .requestMatchers("/api/v1/users", "/api/v1/login").permitAll()

                        // 2. 작품 관련 기능은 '조회(GET)'만 누구나 가능! (중요 ✨)
                        // 찜하기(POST)나 문의하기(POST)는 막힙니다.
                        .requestMatchers(HttpMethod.GET, "/api/v1/artworks/**").permitAll()

                        // 3. 스웨거 문서는 누구나 가능
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        // 4. 나머지는 다 로그인해야 함
                        .anyRequest().authenticated()
                );

        // TODO: 나중에 여기에 'JwtAuthenticationFilter'를 추가해야 합니다.
        // .addFilterBefore(new JwtAuthenticationFilter(...), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}