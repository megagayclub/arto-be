package com.arto.arto.domain.orders.repository;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> findByBuyer(UsersEntity buyer);
}
