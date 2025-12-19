package com.arto.arto.domain.orders.service;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.cart_items.entity.CartItemsEntity;
import com.arto.arto.domain.cart_items.repository.CartItemsRepository;
import com.arto.arto.domain.orders.dto.request.OrderCheckoutRequest;
import com.arto.arto.domain.orders.dto.request.OrderCreateRequest;
import com.arto.arto.domain.orders.dto.request.ShippingInfoUpdateRequest;
import com.arto.arto.domain.orders.dto.response.OrderHistoryResponse;
import com.arto.arto.domain.orders.dto.response.OrderResponse;
import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.orders.type.OrderStatus;
import com.arto.arto.domain.payments.entity.PaymentsEntity;
import com.arto.arto.domain.payments.repository.PaymentsRepository;
import com.arto.arto.domain.payments.service.PaymentsService;
import com.arto.arto.domain.payments.type.PaymentMethod;
import com.arto.arto.domain.payments.type.PaymentStatus;
import com.arto.arto.domain.shopping_carts.entity.ShoppingCartsEntity;
import com.arto.arto.domain.shopping_carts.repository.ShoppingCartsRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final ShoppingCartsRepository shoppingCartsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ArtworkRepository artworkRepository;

    private final PaymentsRepository paymentsRepository; // ✅ 추가
    private final PaymentsService paymentsService;

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
        order.setTotalAmount(artwork.getPrice());
        order.setOrderStatus(OrderStatus.PENDING);
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

        UsersEntity buyer = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "사용자를 찾을 수 없습니다."
                ));

        ShoppingCartsEntity cart = shoppingCartsRepository.findByUser(buyer)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.BAD_REQUEST.value(),
                        "장바구니가 존재하지 않습니다."
                ));

        List<CartItemsEntity> cartItems = cartItemsRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST.value(),
                    "장바구니가 비어 있습니다."
            );
        }

        // ✅ 결제수단
        PaymentMethod method = request.getPaymentMethod();

        List<OrderResponse> result = new ArrayList<>();

        for (CartItemsEntity cartItem : cartItems) {
            ArtworkEntity artwork = cartItem.getArtwork();

            OrdersEntity order = new OrdersEntity();
            order.setBuyer(buyer);
            order.setArtwork(artwork);
            order.setOrderDate(LocalDate.now());
            order.setTotalAmount(artwork.getPrice());
            order.setOrderStatus(OrderStatus.PENDING);

            order.setPostCode(request.getPostCode());
            order.setShippingAddress(request.getShippingAddress());
            order.setShippingPhoneNumber(request.getShippingPhoneNumber());
            order.setReceiverName(request.getReceiverName());

            OrdersEntity savedOrder = ordersRepository.save(order);

            // ===========================
            // ✅ 결제 생성 + 즉시 확정 처리
            // ===========================
            PaymentsEntity payment = new PaymentsEntity();
            payment.setOrder(savedOrder);
            payment.setPaymentAmount(savedOrder.getTotalAmount());
            payment.setPaymentMethod(method);
            payment.setPaymentStatus(PaymentStatus.CONFIRMED); // ✅ 지금은 바로 결제완료 처리
            payment.setPaymentDate(LocalDateTime.now());
            payment.setTransactionId(UUID.randomUUID().toString()); // ✅ 임시 트랜잭션ID

            paymentsRepository.save(payment);

            // ✅ 주문 상태도 결제완료로 변경
            savedOrder.setOrderStatus(OrderStatus.PAID);
            // dirty checking으로 반영됨 (굳이 save 안 해도 됨)
            // ordersRepository.save(savedOrder);

            result.add(OrderResponse.fromEntity(savedOrder));
        }

        // ✅ 장바구니 비우기
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

    //배송 정보 업데이트 메서드
    @Transactional
    public OrderResponse updateShippingInfo(Long orderId, ShippingInfoUpdateRequest request) {

        OrdersEntity order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "주문을 찾을 수 없습니다."
                ));

        order.setShippingCarrier(request.getShippingCarrier());
        order.setTrackingNumber(request.getTrackingNumber());

        // 처음 배송정보 입력되는 시점에 배송 시작 처리도 같이 할 수 있음
        if (order.getDeliveryStartDate() == null) {
            order.setDeliveryStartDate(LocalDate.now());
        }
        order.setOrderStatus(OrderStatus.SHIPPED); // 상태도 변경 (원하면)

        OrdersEntity saved = ordersRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }

    // 배송 완료 처리
    @Transactional
    public OrderResponse completeDelivery(Long orderId) {

        OrdersEntity order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "주문을 찾을 수 없습니다."
                ));

        // 이미 배송 완료된 주문이면 막을지, 그냥 두실지는 자유
        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST.value(),
                    "이미 배송 완료된 주문입니다."
            );
        }

        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveryCompletedDate(LocalDate.now());

        OrdersEntity saved = ordersRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }

    // ✅ 내 주문(구매) 이력 + 결제 정보까지 포함해서 반환
    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> getMyOrderHistory(Long userId) {

        List<OrdersEntity> orders = ordersRepository.findByBuyerIdOrderByOrderDateDesc(userId);

        return orders.stream()
                .map(order -> {
                    // ✅ 결제 정보가 없을 수도 있음 (주문만 있고 결제 생성 전)
                    PaymentsEntity payment = paymentsRepository.findByOrder_Id(order.getId()).orElse(null);

                    return OrderHistoryResponse.builder()
                            .orderId(order.getId())
                            .artworkTitle(order.getArtwork() != null ? order.getArtwork().getTitle() : "정보 없음")
                            .artistName(order.getArtwork() != null ? order.getArtwork().getArtistName() : "작가 미상")
                            .thumbnailUrl(order.getArtwork() != null ? order.getArtwork().getThumbnailImageUrl() : null)
                            .totalAmount(order.getTotalAmount())
                            .orderDate(order.getOrderDate())
                            .orderStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null)

                            // ✅ 결제 필드 채우기 (없으면 null)
                            .paymentId(payment != null ? payment.getPaymentId() : null)
                            .paymentStatus(payment != null && payment.getPaymentStatus() != null ? payment.getPaymentStatus().name() : null)
                            .paymentMethod(payment != null && payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null)
                            .paymentDate(payment != null ? payment.getPaymentDate() : null)
                            .transactionId(payment != null ? payment.getTransactionId() : null)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
