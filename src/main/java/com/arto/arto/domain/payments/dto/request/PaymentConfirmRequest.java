package com.arto.arto.domain.payments.dto.request;

import lombok.Getter;

@Getter
public class PaymentConfirmRequest {
    private Long paymentId;
    private String transactionId; // 지금은 null 가능
}

