package com.arto.arto.domain.payments.dto.response;

import com.arto.arto.domain.payments.type.PaymentMethod;
import com.arto.arto.domain.payments.type.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private BigDecimal paymentAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
}

