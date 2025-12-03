package com.arto.arto;

import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role; // Role Enum 위치 확인!
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminMakerTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Transactional
    @Rollback(false) //
    void makeUserAdmin() {
        // 1. 승진시킬 이메일 입력
        String targetEmail = "test@example.com";

        // 2. 유저 찾기
        UsersEntity user = usersRepository.findByEmail(targetEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 3. 역할 변경 (USER -> ADMIN)
        user.setRole(Role.ADMIN);

        // 4. 저장 (명시적으로 save 호출)
        usersRepository.save(user);

        System.out.println("==========================================");
        System.out.println(targetEmail + " admin으로 변경");
        System.out.println("==========================================");
    }
}