package com.arto.arto.domain.orders.controller;

import com.arto.arto.domain.orders.dto.request.OrderCreateRequest;
import com.arto.arto.domain.orders.dto.response.OrderResponse;
import com.arto.arto.domain.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    // 주문 생성
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        return ordersService.createOrder(request);
    }

    // 주문 상세 조회
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return ordersService.getOrder(id);
    }

    // 특정 유저 주문 목록 조회
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getUserOrders(@PathVariable Long userId) {
        return ordersService.getUserOrders(userId);
    }
}
