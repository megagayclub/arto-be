package com.arto.arto.domain.admins.repository;

import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminsRepository extends JpaRepository<AdminsEntity, Long> {
    Optional<AdminsEntity> findByUser(UsersEntity user);
}