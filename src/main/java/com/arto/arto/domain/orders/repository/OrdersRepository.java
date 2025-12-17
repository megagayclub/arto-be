package com.arto.arto.domain.orders.repository;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> findByBuyer(UsersEntity buyer);

    @EntityGraph(attributePaths = {"artwork"})
    @Query("SELECT o FROM OrdersEntity o WHERE o.buyer.userId = :buyerId ORDER BY o.orderDate DESC")
    List<OrdersEntity> findByBuyerIdOrderByOrderDateDesc(@Param("buyerId") Long buyerId);
}
