package com.arto.arto.domain.payments.entity;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.payments.type.PaymentMethod;
import com.arto.arto.domain.payments.type.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payments")
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrdersEntity order;

    @Column(name = "payment_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal paymentAmount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_id", unique = true, length = 100)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

}
