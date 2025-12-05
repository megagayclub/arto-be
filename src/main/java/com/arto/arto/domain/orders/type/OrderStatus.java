package com.arto.arto.domain.orders.type;

public enum OrderStatus {

    // 주문만 만들어진 상태 (장바구니 → 주문 생성 직후)
    PENDING,

    // 결제까지 완료된 상태 (결제 시스템 붙으면 사용)
    PAID,

    // 포장/출고 준비 중
    PREPARING,

    // 택배사에 넘겨서 배송이 시작된 상태
    SHIPPED,

    // 고객에게 배송 완료
    DELIVERED,

    // 주문 취소
    CANCELLED
}
