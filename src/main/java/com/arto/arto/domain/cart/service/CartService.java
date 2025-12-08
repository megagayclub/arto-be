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

    // 장바구니 조회
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

    // 장바구니에 아이템 추가
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

    // 장바구니에서 아이템 삭제
    @Transactional
    public CartResponse removeItem(Long userId, Long cartItemId) {
        CartItemsEntity item = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "장바구니 항목을 찾을 수 없습니다."
                ));

        cartItemsRepository.delete(item);

        return getCart(userId);
    }

    // 유틸 메서드들
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
}
