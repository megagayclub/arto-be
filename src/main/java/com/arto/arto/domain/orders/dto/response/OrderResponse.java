package com.arto.arto.domain.orders.dto.response;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {

    private Long orderId;
    private Long buyerId;
    private String buyerName;

    private Long artworkId;
    private String artworkTitle;

    private String orderDate;
    private Integer totalAmount;
    private String orderStatus;

    private Integer postCode;
    private String shippingAddress;
    private String shippingPhoneNumber;
    private String receiverName;

    private String deliveryStartDate;
    private String deliveryCompletedDate;

    public static OrderResponse fromEntity(OrdersEntity order) {
        return OrderResponse.builder()
//                .orderId(order.getId())
//                .buyerId(order.getBuyer().getUserId())
//                .buyerName(order.getBuyer().getName())
//                .artworkId(order.getArtwork().getArtworkId())
//                .artworkTitle(order.getArtwork().getTitle())
//                .orderDate(order.getOrderDate().toString())
//                .totalAmount(order.getTotalAmount())
//                .orderStatus(order.getOrderStatus())
//                .postCode(order.getPostCode())
//                .shippingAddress(order.getShippingAddress())
//                .shippingPhoneNumber(order.getShippingPhoneNumber())
//                .receiverName(order.getReceiverName())
//                .deliveryStartDate(order.getDeliveryStartDate() != null ? order.getDeliveryStartDate().toString() : null)
//                .deliveryCompletedDate(order.getDeliver() != null ? order.getDeliver().toString() : null)
                .build();
    }
}
