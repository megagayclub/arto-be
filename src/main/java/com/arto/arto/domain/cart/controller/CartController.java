package com.arto.arto.domain.cart.controller;

import com.arto.arto.domain.cart.dto.request.CartItemAddRequest;
import com.arto.arto.domain.cart.dto.response.CartResponse;
import com.arto.arto.domain.cart.service.CartService;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart") // ✅ v1로 통일 (추천)
public class CartController {

    private final CartService cartService;
    private final UsersRepository usersRepository; // ✅ 추가

    // ✅ 내 장바구니 조회
    @GetMapping
    public CartResponse getMyCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        return cartService.getCart(userId);
    }

    // ✅ 내 장바구니에 아이템 추가
    @PostMapping("/items")
    public CartResponse addItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartItemAddRequest request
    ) {
        Long userId = getUserId(userDetails);
        return cartService.addItem(userId, request);
    }

    // ✅ 내 장바구니 아이템 삭제
    @DeleteMapping("/items/{cartItemId}")
    public CartResponse removeItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartItemId
    ) {
        Long userId = getUserId(userDetails);
        return cartService.removeItem(userId, cartItemId);
    }

    // 🔹 공통: email -> userId 변환
    private Long getUserId(UserDetails userDetails) {
        String email = userDetails.getUsername();
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
        return user.getUserId();
    }
}
