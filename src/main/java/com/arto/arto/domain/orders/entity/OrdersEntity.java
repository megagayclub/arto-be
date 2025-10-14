package com.arto.arto.domain.orders.entity;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

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
    private Integer totalAmount;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

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
    private LocalDate deliver;
}
