package com.arto.arto.domain.orders.controller;

import com.arto.arto.domain.orders.dto.request.OrderCheckoutRequest;
import com.arto.arto.domain.orders.dto.request.OrderCreateRequest;
import com.arto.arto.domain.orders.dto.request.ShippingInfoUpdateRequest;
import com.arto.arto.domain.orders.dto.response.OrderHistoryResponse;
import com.arto.arto.domain.orders.dto.response.OrderResponse;
import com.arto.arto.domain.orders.service.OrdersService;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.global.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final UsersRepository usersRepository;

    // 🎯 단일 주문 생성 (바로 작품에서 주문할 때)
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        return ordersService.createOrder(request);
    }

    @PostMapping("/checkout")
    public List<OrderResponse> checkoutFromCart(
            @Valid @RequestBody OrderCheckoutRequest request,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        Long currentUserId = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."
                ))
                .getUserId();

        return ordersService.checkoutFromCart(currentUserId, request);
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

    @GetMapping("/my")
    public ResponseEntity<List<OrderHistoryResponse>> getMyOrders(
            @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        Long currentUserId = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."
                ))
                .getUserId(); // UsersEntity 필드명이 userId니까 이거 맞음

        return ResponseEntity.ok(ordersService.getMyOrderHistory(currentUserId));
    }
}
