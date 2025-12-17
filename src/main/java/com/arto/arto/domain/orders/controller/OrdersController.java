package com.arto.arto.domain.orders.controller;

import com.arto.arto.domain.orders.dto.request.OrderCheckoutRequest;
import com.arto.arto.domain.orders.dto.request.OrderCreateRequest;
import com.arto.arto.domain.orders.dto.request.ShippingInfoUpdateRequest;
import com.arto.arto.domain.orders.dto.response.OrderHistoryResponse;
import com.arto.arto.domain.orders.dto.response.OrderResponse;
import com.arto.arto.domain.orders.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    // 🎯 단일 주문 생성 (바로 작품에서 주문할 때)
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        return ordersService.createOrder(request);
    }

    // 🎯 장바구니 → 주문 여러 개 생성
    @PostMapping("/checkout/{userId}")
    public List<OrderResponse> checkoutFromCart(
            @PathVariable Long userId,
            @Valid @RequestBody OrderCheckoutRequest request
    ) {
        return ordersService.checkoutFromCart(userId, request);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return ordersService.getOrder(orderId);
    }

    // 특정 유저 주문 목록 조회
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getUserOrders(@PathVariable Long userId) {
        return ordersService.getUserOrders(userId);
    }

    //관리자 페이지에서 쇼핑 정보 업데이트 시킴
    @PatchMapping("/{orderId}/shipping")
    public OrderResponse updateShippingInfo(
            @PathVariable Long orderId,
            @Valid @RequestBody ShippingInfoUpdateRequest request
    ) {
        return ordersService.updateShippingInfo(orderId, request);
    }

    // ✅ 배송 완료 처리
    @PatchMapping("/{orderId}/complete")
    public OrderResponse completeDelivery(@PathVariable Long orderId) {
        return ordersService.completeDelivery(orderId);
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderHistoryResponse>> getMyOrders() {
        // 위에서 선언한 변수명인 ordersService (s 포함)로 호출해야 합니다!
        Long currentUserId = 1L;
        return ResponseEntity.ok(ordersService.getMyOrderHistory(currentUserId));
    }
}
