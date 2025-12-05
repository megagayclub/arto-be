package com.arto.arto.domain.shopping_carts.repository;

import com.arto.arto.domain.shopping_carts.entity.ShoppingCartsEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartsRepository extends JpaRepository<ShoppingCartsEntity, Long> {

    Optional<ShoppingCartsEntity> findByUser(UsersEntity user);
}
