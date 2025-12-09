package com.arto.arto.domain.users.repository;

import com.arto.arto.domain.users.entity.PasswordResetTokenEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    // 토큰으로 찾기
    Optional<PasswordResetTokenEntity> findByToken(String token);

    // 유저로 찾기 (이미 발급된 토큰이 있는지 확인할 때 사용)
    Optional<PasswordResetTokenEntity> findByUser(UsersEntity user);

    // 해당 유저의 토큰 삭제 (비번 변경 완료 후 청소용)
    void deleteByUser(UsersEntity user);
}