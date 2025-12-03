package com.arto.arto.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long tokenValidityInMilliseconds;

    // application.yml에서 설정한 secret 값과 만료 시간을 가져옵니다.
    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.expiration}") long tokenValidityInMilliseconds) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    /**
     * 토큰 생성 메서드
     * @param email 사용자 이메일 (로그인 ID)
     * @return 생성된 JWT 토큰 문자열
     */
    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(email) // 토큰의 주인 (이메일)
                .setIssuedAt(now) // 발행 시간
                .setExpiration(validity) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 비밀키로 서명 (도장 쾅!)
                .compact();
    }
}