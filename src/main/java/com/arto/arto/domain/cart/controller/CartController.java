package com.arto.arto.domain.cart.controller;

import com.arto.arto.domain.cart.dto.request.CartItemAddRequest;
import com.arto.arto.domain.cart.dto.response.CartResponse;
import com.arto.arto.domain.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart") // ✅ 변경: /api/cart -> /api/v1/cart
public class CartController {

    private final CartService cartService;

    // ✅ 변경: userId PathVariable 제거, JWT 기반으로 내 장바구니 조회
    @GetMapping
    public CartResponse getMyCart(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getMyCart(userDetails.getUsername()); // username = email
    }

    // ✅ 변경: userId PathVariable 제거, JWT 기반으로 내 장바구니에 아이템 추가
    @PostMapping("/items")
    public CartResponse addItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartItemAddRequest request
    ) {
        return cartService.addItemToMyCart(userDetails.getUsername(), request);
    }

    // ✅ 변경: userId PathVariable 제거, JWT 기반으로 내 장바구니 아이템 삭제
    @DeleteMapping("/items/{cartItemId}")
    public CartResponse removeItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartItemId
    ) {
        return cartService.removeItemFromMyCart(userDetails.getUsername(), cartItemId);
    }
}
