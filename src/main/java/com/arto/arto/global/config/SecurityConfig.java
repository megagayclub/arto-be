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
import org.springframework.web.cors.CorsConfiguration; // 👈 1. 추가
import org.springframework.web.cors.CorsConfigurationSource; // 👈 2. 추가
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // 👈 3. 추가

import java.util.Arrays; // 👈 4. 추가

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {



    private final JwtTokenProvider jwtTokenProvider;

    // ==========================================================
    // 💡 1. CORS 설정 Bean 추가
    // ==========================================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 5173 포트를 가진 모든 로컬호스트를 허용합니다. (필요시 다른 포트 추가)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://127.0.0.1:5173",
                "http://52.79.193.77",
                "http://52.79.193.77:81"

        ));





        // Preflight 요청에 필요한 OPTIONS 포함, 필요한 모든 메서드를 허용합니다.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명(인증 토큰 등) 허용
        configuration.setMaxAge(3600L); // Preflight 캐시 시간 1시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 API 경로에 CORS 설정을 적용합니다.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    // ==========================================================

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 💡 2. filterChain에 CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

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
                                "/health",  //test
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
                        .requestMatchers("/api/orders/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/artworks/**").hasRole("ADMIN")


                        // 4. 나머지는 로그인만 하면 됨 (내 정보 수정, 탈퇴 등)
                        .anyRequest().authenticated()


                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}