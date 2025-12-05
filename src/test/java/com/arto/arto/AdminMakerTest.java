package com.arto.arto;

import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder; // 패스워드 인코더 추가
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminMakerTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback(false)
    void makeUserAdmin() {
        String targetEmail = "test@example.com";

        // 1. 유저를 찾는데, 없으면 -> 새로 만들어서 가져와
        UsersEntity user = usersRepository.findByEmail(targetEmail)
                .orElseGet(() -> {
                    System.out.println("유저가 없어서 새로 만들어버림");
                    UsersEntity newUser = UsersEntity.builder()
                            .email(targetEmail)
                            .password(passwordEncoder.encode("Password123!"))
                            .name("슈퍼관리자")
                            .role(Role.ADMIN) // 태어날 때부터 관리자
                            .isActive(true)
                            .build();
                    return usersRepository.save(newUser);
                });

        if (!user.getPassword().startsWith("$2a$")) { // 암호화 안 된 거라면
            user.setPassword(passwordEncoder.encode("Password123!"));
        }

        // 2. 혹시 이미 있던 유저라면 관리자로 등급 변경
        if (user.getRole() != Role.ADMIN) {
            user.setRole(Role.ADMIN);
            usersRepository.save(user);
        }

        System.out.println(targetEmail + " 계정이 관리자가 되어버림");
    }
}