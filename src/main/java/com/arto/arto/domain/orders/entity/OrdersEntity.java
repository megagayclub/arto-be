package com.arto.arto.domain.orders.entity;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.orders.type.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbl_orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private UsersEntity buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    private ArtworkEntity artwork;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 20)
    private OrderStatus orderStatus;

    @Column(name = "post_code", nullable = false)
    private Integer postCode;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "shipping_phone_number", nullable = false)
    private String shippingPhoneNumber;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "delivery_start_date")
    private LocalDate deliveryStartDate;

    @Column(name = "delivery_completed_date")
    private LocalDate deliveryCompletedDate;

    @Column(name = "shipping_carrier")  // 예: CJ대한통운, 로젠, 일본엔 야마토 등
    private String shippingCarrier;

    @Column(name = "tracking_number")   // 운송장 번호
    private String trackingNumber;


}
