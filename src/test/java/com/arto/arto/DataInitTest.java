package com.arto.arto;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.entity.ColorEntity;
import com.arto.arto.domain.artwork.entity.MoodEntity;
import com.arto.arto.domain.artwork.entity.SpaceEntity;
import com.arto.arto.domain.artwork.repository.ColorRepository;
import com.arto.arto.domain.artwork.repository.MoodRepository;
import com.arto.arto.domain.artwork.repository.SpaceRepository;
import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.arto.arto.domain.orders.type.OrderStatus;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import java.time.LocalDate;
import java.util.List;
import com.arto.arto.domain.users.repository.UsersRepository;
@SpringBootTest
public class DataInitTest {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    @Test
    @Transactional
    @Rollback(false) // ✨ 핵심! 테스트가 끝나도 데이터를 지우지 않고 DB에 남깁니다.
    void initTagData() {

        // 1. 색상 (Colors) 데이터 넣기
        if (colorRepository.count() == 0) {
            colorRepository.saveAll(List.of(
                    new ColorEntity("Red"), new ColorEntity("Blue"),
                    new ColorEntity("Green"), new ColorEntity("Yellow"),
                    new ColorEntity("Black"), new ColorEntity("White")
            ));
        }

        // 2. 공간 (Spaces) 데이터 넣기
        if (spaceRepository.count() == 0) {
            spaceRepository.saveAll(List.of(
                    new SpaceEntity("Living Room"), new SpaceEntity("Bedroom"),
                    new SpaceEntity("Kitchen"), new SpaceEntity("Office"),
                    new SpaceEntity("Hallway")
            ));
        }

        // 3. 분위기 (Moods) 데이터 넣기
        if (moodRepository.count() == 0) {
            moodRepository.saveAll(List.of(
                    new MoodEntity("Modern"), new MoodEntity("Cozy"),
                    new MoodEntity("Vintage"), new MoodEntity("Minimal"),
                    new MoodEntity("Vibrant")
            ));
        }

        System.out.println("==========================================");
        System.out.println("데이터 넣음");
        System.out.println("==========================================");
    }

    @Test
    @Transactional
    @Rollback(false)
    void initOrderData() {
        // 1. 기존에 생성한 1번 유저와 1번 작품을 가져옵니다.
        // 만약 유저 리포지토리에 데이터가 없다면 먼저 유저부터 생성해야 합니다.
        UsersEntity buyer = usersRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("1번 유저를 찾을 수 없습니다."));

        ArtworkEntity artwork = artworkRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("1번 작품을 찾을 수 없습니다."));

        // 2. 주문 엔티티 생성
        OrdersEntity order = new OrdersEntity();
        order.setBuyer(buyer);
        order.setArtwork(artwork);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(artwork.getPrice());

        // 4. OrderStatus 사용 (정의하신 PAID나 SHIPPED 중 선택)
        order.setOrderStatus(OrderStatus.DELIVERED);

        order.setPostCode(12345);
        order.setShippingAddress("대구광역시 중구 중앙대로");
        order.setShippingPhoneNumber("010-1234-5678");
        order.setReceiverName("김페페");

        // 3. 저장
        ordersRepository.save(order);

        System.out.println("==========================================");
        System.out.println("주문 데이터 생성 성공: " + artwork.getTitle());
        System.out.println("==========================================");
    }

    @Test
    void findMyId() {
        usersRepository.findAll().forEach(user -> {
            System.out.println("유저 이름: " + user.getEmail() + " | 유저 ID: " + user.getUserId());
        });
    }
}