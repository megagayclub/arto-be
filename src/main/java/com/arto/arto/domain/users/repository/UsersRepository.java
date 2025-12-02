package com.arto.arto.domain.users.repository;

import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}