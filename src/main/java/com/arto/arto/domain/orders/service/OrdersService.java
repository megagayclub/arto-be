package com.arto.arto.domain.orders.service;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.cart_items.entity.CartItemsEntity;
import com.arto.arto.domain.cart_items.repository.CartItemsRepository;
import com.arto.arto.domain.orders.dto.request.OrderCheckoutRequest;
import com.arto.arto.domain.orders.dto.request.OrderCreateRequest;
import com.arto.arto.domain.orders.dto.response.OrderResponse;
import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.shopping_carts.entity.ShoppingCartsEntity;
import com.arto.arto.domain.shopping_carts.repository.ShoppingCartsRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final ShoppingCartsRepository shoppingCartsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ArtworkRepository artworkRepository;

    // 주문 생성
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {

        UsersEntity buyer = usersRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다."));

        ArtworkEntity artwork = artworkRepository.findById(request.getArtworkId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "작품을 찾을 수 없습니다."));

        OrdersEntity order = new OrdersEntity();

        order.setBuyer(buyer);
        order.setArtwork(artwork);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderStatus("PENDING");
        order.setPostCode(request.getPostCode());
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingPhoneNumber(request.getShippingPhoneNumber());
        order.setReceiverName(request.getReceiverName());

        ordersRepository.save(order);

        return OrderResponse.fromEntity(order);
    }


    // ✅ 장바구니 → 주문 여러 개 생성 (체크아웃)
    @Transactional
    public List<OrderResponse> checkoutFromCart(Long userId, OrderCheckoutRequest request) {

        // 1. 유저 검증
        UsersEntity buyer = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."
                ));

        // 2. 유저의 장바구니 조회
        ShoppingCartsEntity cart = shoppingCartsRepository.findByUser(buyer)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.BAD_REQUEST.value(),
                        "장바구니가 존재하지 않습니다."
                ));

        // 3. 장바구니 아이템 조회
        List<CartItemsEntity> cartItems = cartItemsRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST.value(),
                    "장바구니가 비어 있습니다."
            );
        }

        List<OrderResponse> result = new ArrayList<>();

        // 4. 각 CartItem → OrdersEntity 생성
        for (CartItemsEntity cartItem : cartItems) {
            ArtworkEntity artwork = cartItem.getArtwork();

            OrdersEntity order = new OrdersEntity();
            order.setBuyer(buyer);
            order.setArtwork(artwork);
            order.setOrderDate(LocalDate.now());
            // ArtworkEntity.price는 BigDecimal, OrdersEntity.totalAmount는 Integer라 캐스팅 필요
            order.setTotalAmount(artwork.getPrice());
            order.setOrderStatus("PENDING"); // 기본 상태
            order.setPostCode(request.getPostCode());
            order.setShippingAddress(request.getShippingAddress());
            order.setShippingPhoneNumber(request.getShippingPhoneNumber());
            order.setReceiverName(request.getReceiverName());

            OrdersEntity saved = ordersRepository.save(order);
            result.add(OrderResponse.fromEntity(saved));
        }

        // 5. 장바구니 비우기
        cartItemsRepository.deleteAll(cartItems);

        return result;
    }


    // 주문 단건 조회
    public OrderResponse getOrder(Long id) {
        OrdersEntity order = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "주문을 찾을 수 없습니다."));

        return OrderResponse.fromEntity(order);
    }

    // 특정 사용자 주문 목록 조회
    public List<OrderResponse> getUserOrders(Long userId) {

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."));

        return ordersRepository.findByBuyer(user)
                .stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }
}
