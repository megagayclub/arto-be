package com.arto.arto.domain.cart.controller;

import com.arto.arto.domain.cart.dto.request.CartItemAddRequest;
import com.arto.arto.domain.cart.dto.response.CartResponse;
import com.arto.arto.domain.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    // 장바구니에 아이템 추가
    @PostMapping("/{userId}/items")
    public CartResponse addItem(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemAddRequest request
    ) {
        return cartService.addItem(userId, request);
    }

    // 장바구니 아이템 삭제
    @DeleteMapping("/{userId}/items/{cartItemId}")
    public CartResponse removeItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId
    ) {
        return cartService.removeItem(userId, cartItemId);
    }
}
