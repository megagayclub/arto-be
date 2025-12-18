package com.arto.arto.domain.orders.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {
    private Long orderId;           // 주문 번호
    private String artworkTitle;    // 작품 제목
    private String artistName;      // 작가 이름
    private String thumbnailUrl;    // 작품 썸네일 이미지 URL
    private BigDecimal totalAmount; // 총 결제 금액
    private LocalDate orderDate;    // 주문 날짜
    private String orderStatus;     // 주문 상태 (문자열로 변환)

    // ✅ 결제 정보 추가
    private Long paymentId;         // 결제 PK
    private String paymentStatus;   // PENDING / CONFIRMED
    private String paymentMethod;   // CARD / BANK_TRANSFER / VIRTUAL_ACCOUNT
    private LocalDateTime paymentDate; // 결제 시각
    private String transactionId;   // 선택
}