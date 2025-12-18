package com.arto.arto.domain.payments.dto.request;

import com.arto.arto.domain.payments.type.PaymentMethod;

public class PaymentReadyRequest {
    private Long orderId;
    private PaymentMethod paymentMethod;
}
