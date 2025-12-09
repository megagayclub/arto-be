package com.arto.arto.domain.orders.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCheckoutRequest {

    @NotNull(message = "우편번호를 입력해주세요.")
    private Integer postCode;

    @NotBlank(message = "배송지를 입력해주세요.")
    private String shippingAddress;

    @NotBlank(message = "배송 연락처를 입력해주세요.")
    private String shippingPhoneNumber;

    @NotBlank(message = "수령인 이름을 입력해주세요.")
    private String receiverName;
}
