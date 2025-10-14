package kr.co.ync.board.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import kr.co.ync.board.global.jwt.enums.JwtType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {
    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void testAccessToken() {
        String email = "user1@aaa.com";
        String accessToken = jwtProvider.generateAccessToken(email);
        System.out.println("==================================");
        System.out.println(accessToken);
        System.out.println("==================================");
        Jws<Claims> claims = jwtProvider
                .getClaims(accessToken);

        if(jwtProvider.isWrongType(claims, JwtType.REFRESH)){
            System.out.println("X");
        }

        String subject = claims.getPayload().getSubject();
        System.out.println("==================================");
        System.out.println(subject);
    }
}