package com.arto.arto.domain.payments.controller;

import com.arto.arto.domain.payments.dto.request.PaymentConfirmRequest;
import com.arto.arto.domain.payments.dto.request.PaymentReadyRequest;
import com.arto.arto.domain.payments.service.PaymentsService;
import com.arto.arto.domain.payments.type.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    /**
     * ✅ 결제 생성(READY)
     * POST /api/payments/ready
     */
    @PostMapping("/ready")
    public PaymentsService.PaymentResponse ready(@RequestBody PaymentReadyRequest request) {
        return paymentsService.readyPayment(request.getOrderId(), request.getPaymentMethod());
    }

    /**
     * ✅ 결제 확정(CONFIRM)
     * POST /api/payments/confirm
     */
    @PostMapping("/confirm")
    public PaymentsService.PaymentResponse confirm(@RequestBody PaymentConfirmRequest request) {
        return paymentsService.confirmPayment(request.getPaymentId(), request.getTransactionId());
    }

    /**
     * ✅ 주문ID로 결제 조회
     * GET /api/payments/order/{orderId}
     */
    @GetMapping("/order/{orderId}")
    public PaymentsService.PaymentResponse getByOrder(@PathVariable Long orderId) {
        return paymentsService.getPaymentByOrderId(orderId);
    }

}