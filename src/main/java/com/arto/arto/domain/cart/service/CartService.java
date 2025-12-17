package com.arto.arto.domain.cart.service;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.cart.dto.request.CartItemAddRequest;
import com.arto.arto.domain.cart.dto.response.CartItemResponse;
import com.arto.arto.domain.cart.dto.response.CartResponse;
import com.arto.arto.domain.cart_items.entity.CartItemsEntity;
import com.arto.arto.domain.cart_items.repository.CartItemsRepository;
import com.arto.arto.domain.shopping_carts.entity.ShoppingCartsEntity;
import com.arto.arto.domain.shopping_carts.repository.ShoppingCartsRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final ShoppingCartsRepository shoppingCartsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UsersRepository usersRepository;
    private final ArtworkRepository artworkRepository;

    // =========================================================
    // ✅ [추가] JWT 기반 API용 래퍼 메서드들 (email -> userId 변환)
    // =========================================================

    /**
     * ✅ 추가: 토큰(=email) 기반으로 내 장바구니 조회
     * 프론트: GET /api/v1/cart
     */
    public CartResponse getMyCart(String email) {
        Long userId = getUserIdByEmail(email); // ✅ 추가
        return getCart(userId);                // ✅ 기존 로직 재사용
    }

    /**
     * ✅ 추가: 토큰(=email) 기반으로 내 장바구니에 아이템 추가
     * 프론트: POST /api/v1/cart/items
     */
    @Transactional
    public CartResponse addItemToMyCart(String email, CartItemAddRequest request) {
        Long userId = getUserIdByEmail(email); // ✅ 추가
        return addItem(userId, request);       // ✅ 기존 로직 재사용
    }

    /**
     * ✅ 추가: 토큰(=email) 기반으로 내 장바구니에서 아이템 삭제
     * 프론트: DELETE /api/v1/cart/items/{cartItemId}
     */
    @Transactional
    public CartResponse removeItemFromMyCart(String email, Long cartItemId) {
        Long userId = getUserIdByEmail(email); // ✅ 추가
        return removeItem(userId, cartItemId); // ✅ 기존 로직 재사용
    }

    // =========================================================
    // ✅ 기존 로직들 (userId 기반) - 최대한 유지
    // =========================================================

    // 장바구니 조회 (기존)
    public CartResponse getCart(Long userId) {
        UsersEntity user = getUser(userId);

        ShoppingCartsEntity cart = shoppingCartsRepository.findByUser(user)
                .orElse(null);

        if (cart == null) {
            return CartResponse.builder()
                    .cartId(null)
                    .userId(user.getUserId())
                    .items(List.of())
                    .totalAmount(BigDecimal.ZERO)
                    .build();
        }

        List<CartItemsEntity> items = cartItemsRepository.findByCart(cart);

        List<CartItemResponse> itemResponses = items.stream()
                .map(CartItemResponse::fromEntity)
                .toList();

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(user.getUserId())
                .items(itemResponses)
                .totalAmount(total)
                .build();
    }

    // 장바구니에 아이템 추가 (기존)
    @Transactional
    public CartResponse addItem(Long userId, CartItemAddRequest request) {
        UsersEntity user = getUser(userId);
        ArtworkEntity artwork = getArtwork(request.getArtworkId());

        // 1. 장바구니 없으면 생성
        ShoppingCartsEntity cart = shoppingCartsRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCartsEntity newCart = new ShoppingCartsEntity();
                    newCart.setUser(user);
                    return shoppingCartsRepository.save(newCart);
                });

        // 2. 이미 해당 artwork가 장바구니에 있으면 그대로 (중복 추가 X)
        boolean exists = cartItemsRepository.findByCartAndArtwork(cart, artwork).isPresent();
        if (!exists) {
            CartItemsEntity newItem = new CartItemsEntity();
            newItem.setCart(cart);
            newItem.setArtwork(artwork);
            cartItemsRepository.save(newItem);
        }

        return getCart(userId);
    }

    // 장바구니에서 아이템 삭제 (기존)
    @Transactional
    public CartResponse removeItem(Long userId, Long cartItemId) {
        CartItemsEntity item = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "장바구니 항목을 찾을 수 없습니다."
                ));

        // ✅ 권한 체크(선택): 내 장바구니 아이템인지 검증하고 싶으면 여기서 추가 가능
        cartItemsRepository.delete(item);

        return getCart(userId);
    }

    // =========================================================
    // ✅ 유틸 메서드들
    // =========================================================

    private UsersEntity getUser(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."
                ));
    }

    private ArtworkEntity getArtwork(Long artworkId) {
        return artworkRepository.findById(artworkId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "작품을 찾을 수 없습니다."
                ));
    }

    /**
     * ✅ 추가: 이메일로 userId 찾기 (JWT 기반용 핵심)
     */
    private Long getUserIdByEmail(String email) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."
                ));
        return user.getUserId();
    }
}
