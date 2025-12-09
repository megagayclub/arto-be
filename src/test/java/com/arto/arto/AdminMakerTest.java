package com.arto.arto;

import com.arto.arto.domain.admins.entity.AdminsEntity; // 👈 import 추가됨
import com.arto.arto.domain.admins.repository.AdminsRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminMakerTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminsRepository adminsRepository;

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
                            .role(Role.ADMIN)
                            .isActive(true)
                            .build();
                    return usersRepository.save(newUser);
                });

        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode("Password123!"));
        }

        // 2. 혹시 이미 있던 유저라면 관리자로 등급 변경
        if (user.getRole() != Role.ADMIN) {
            user.setRole(Role.ADMIN);
            usersRepository.save(user);
        }

        if (adminsRepository.findByUser(user).isEmpty()) {
            AdminsEntity admin = AdminsEntity.builder()
                    .user(user)
                    .adminLevel(1) // 관리자 레벨 (임의로 1 설정)
                    .build();
            adminsRepository.save(admin);
            System.out.println("tbl_admins 테이블에도 관리자 정보 등록 완료");
        }

        System.out.println(targetEmail + " 계정이 완벽한 관리자가 되어버림");
    }
}