package com.arto.arto.domain.orders.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingInfoUpdateRequest {

    @NotBlank(message = "택배사를 입력해주세요.")
    private String shippingCarrier;

    @NotBlank(message = "운송장 번호를 입력해주세요.")
    private String trackingNumber;
}
