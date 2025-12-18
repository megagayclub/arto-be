package com.arto.arto.domain.payments.repository;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.payments.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {
    // 주문 1건당 결제 1건 (중복 생성 방지 / 조회)
    Optional<PaymentsEntity> findByOrder(OrdersEntity order);

    // 주문ID로 결제 조회
    Optional<PaymentsEntity> findByOrder_Id(Long orderId);
}

