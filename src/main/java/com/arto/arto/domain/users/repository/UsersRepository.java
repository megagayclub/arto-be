package com.arto.arto.domain.users.repository;

import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    // 1. 회원가입 시 이메일 중복 확인을 위해 사용
    boolean existsByEmail(String email);

    // 2. 로그인 시 이메일로 회원을 찾기 위해 사용
    Optional<UsersEntity> findByEmail(String email);
}