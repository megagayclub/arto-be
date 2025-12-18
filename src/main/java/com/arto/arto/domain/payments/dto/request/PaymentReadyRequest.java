package com.arto.arto.domain.payments.dto.request;

import com.arto.arto.domain.payments.type.PaymentMethod;
import lombok.Getter;

@Getter
public class PaymentReadyRequest {
    private Long orderId;
    private PaymentMethod paymentMethod;
}
