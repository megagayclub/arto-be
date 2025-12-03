package com.arto.arto.domain.orders.service;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.orders.dto.request.OrderCreateRequest;
import com.arto.arto.domain.orders.dto.response.OrderResponse;
import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final ArtworkRepository artworkRepository;

    // 주문 생성
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {

        UsersEntity buyer = usersRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다."));

        ArtworkEntity artwork = artworkRepository.findById(request.getArtworkId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "작품을 찾을 수 없습니다."));

        OrdersEntity order = new OrdersEntity();

//        order.setBuyer(buyer);
//        order.setArtwork(artwork);
//        order.setOrderDate(LocalDate.now());
//        order.setTotalAmount(request.getTotalAmount());
//        order.setOrderStatus("PENDING");
//        order.setPostCode(request.getPostCode());
//        order.setShippingAddress(request.getShippingAddress());
//        order.setShippingPhoneNumber(request.getShippingPhoneNumber());
//        order.setReceiverName(request.getReceiverName());

        ordersRepository.save(order);

        return OrderResponse.fromEntity(order);
    }

    // 주문 단건 조회
    public OrderResponse getOrder(Long id) {
        OrdersEntity order = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "주문을 찾을 수 없습니다."));

        return OrderResponse.fromEntity(order);
    }

    // 특정 사용자 주문 목록 조회
    public List<OrderResponse> getUserOrders(Long userId) {

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다."));

        return ordersRepository.findByBuyer(user)
                .stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }
}
