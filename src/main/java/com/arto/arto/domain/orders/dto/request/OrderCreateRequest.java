package com.arto.arto.domain.orders.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequest {

    @NotNull
    private Long buyerId;

    @NotNull
    private Long artworkId;

    @NotNull
    private Integer totalAmount;

    @NotNull
    private Integer postCode;

    @NotNull
    private String shippingAddress;

    @NotNull
    private String shippingPhoneNumber;

    @NotNull
    private String receiverName;
}
