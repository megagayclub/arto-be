package com.arto.arto.domain.payments.service;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.orders.type.OrderStatus;
import com.arto.arto.domain.payments.entity.PaymentsEntity;
import com.arto.arto.domain.payments.repository.PaymentsRepository;
import com.arto.arto.domain.payments.type.PaymentMethod;
import com.arto.arto.domain.payments.type.PaymentStatus;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;
    private final OrdersRepository ordersRepository;

    /**
     * ✅ 결제 생성(READY)
     * - 주문 1건당 결제 1건만 생성하도록 차단
     * - 결제 상태: PENDING
     */
    @Transactional
    public PaymentResponse readyPayment(Long orderId, PaymentMethod paymentMethod) {
        OrdersEntity order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "주문을 찾을 수 없습니다."
                ));

        // 이미 결제가 생성되어 있으면 막기 (주문 1건당 결제 1건 정책)
        paymentsRepository.findByOrder(order).ifPresent(p -> {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "이미 결제가 생성된 주문입니다.");
        });

        // 주문 금액 기준
        BigDecimal amount = order.getTotalAmount();

        PaymentsEntity payment = new PaymentsEntity();
        payment.setOrder(order);
        payment.setPaymentAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        PaymentsEntity saved = paymentsRepository.save(payment);

        return PaymentResponse.from(saved);
    }

    /**
     * ✅ 결제 확정(CONFIRM)
     * - 결제 상태: CONFIRMED
     * - 주문 상태도 함께 변경: PAID (원하면 PREPARING으로 바로 넘겨도 됨)
     */
    @Transactional
    public PaymentResponse confirmPayment(Long paymentId, String transactionId) {

        PaymentsEntity payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "결제를 찾을 수 없습니다."
                ));

        if (payment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "이미 결제 완료된 건입니다.");
        }

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        if (transactionId != null && !transactionId.isBlank()) {
            payment.setTransactionId(transactionId);
        }

        OrdersEntity order = payment.getOrder();
        // 결제 완료 시 주문 상태 갱신
        order.setOrderStatus(OrderStatus.PAID);

        // payment는 dirty checking으로 저장됨 (save 안 해도 됨)
        return PaymentResponse.from(payment);
    }

    /**
     * ✅ 주문ID로 결제 조회
     */
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        PaymentsEntity payment = paymentsRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "해당 주문의 결제 정보가 없습니다."
                ));

        return PaymentResponse.from(payment);
    }

    // =========================
    // 내부 Response DTO
    // (원하면 dto/response 패키지로 분리해도 됨)
    // =========================
    @lombok.Builder
    @lombok.Getter
    public static class PaymentResponse {
        private Long paymentId;
        private Long orderId;
        private BigDecimal paymentAmount;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus;
        private LocalDateTime paymentDate;
        private String transactionId;

        public static PaymentResponse from(PaymentsEntity p) {
            return PaymentResponse.builder()
                    .paymentId(p.getPaymentId())
                    .orderId(p.getOrder() != null ? p.getOrder().getId() : null)
                    .paymentAmount(p.getPaymentAmount())
                    .paymentMethod(p.getPaymentMethod())
                    .paymentStatus(p.getPaymentStatus())
                    .paymentDate(p.getPaymentDate())
                    .transactionId(p.getTransactionId())
                    .build();
        }
    }
}
