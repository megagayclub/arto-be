package com.arto.arto.domain.orders.dto.response;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderResponse {

    private Long orderId;
    private Long buyerId;
    private String buyerName;

    private Long artworkId;
    private String artworkTitle;

    private String orderDate;
    private BigDecimal totalAmount;

    private String orderStatus;

    private Integer postCode;
    private String shippingAddress;
    private String shippingPhoneNumber;
    private String receiverName;

    private String shippingCarrier;
    private String trackingNumber;

    private String deliveryStartDate;
    private String deliveryCompletedDate;

    public static OrderResponse fromEntity(OrdersEntity order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .buyerId(order.getBuyer().getUserId())
                .buyerName(order.getBuyer().getName())
                .artworkId(order.getArtwork().getId())
                .artworkTitle(order.getArtwork().getTitle())
                .orderDate(order.getOrderDate().toString())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .postCode(order.getPostCode())
                .shippingAddress(order.getShippingAddress())
                .shippingPhoneNumber(order.getShippingPhoneNumber())
                .receiverName(order.getReceiverName())
                .shippingCarrier(order.getShippingCarrier())
                .trackingNumber(order.getTrackingNumber())
                .deliveryStartDate(order.getDeliveryStartDate() != null ? order.getDeliveryStartDate().toString() : null)
                .deliveryCompletedDate(order.getDeliveryCompletedDate() != null ? order.getDeliveryCompletedDate().toString() : null)
                .build();
    }
}
