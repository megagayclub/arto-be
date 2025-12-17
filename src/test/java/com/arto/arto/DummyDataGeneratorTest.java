package com.arto.arto;

import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role;

import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.admins.repository.AdminsRepository;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.entity.ColorEntity;
import com.arto.arto.domain.artwork.entity.MoodEntity;
import com.arto.arto.domain.artwork.entity.SpaceEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.artwork.repository.ColorRepository;
import com.arto.arto.domain.artwork.repository.MoodRepository;
import com.arto.arto.domain.artwork.repository.SpaceRepository;
import com.arto.arto.domain.artwork.type.ArtworkStatus;
import com.arto.arto.domain.artwork.type.Morph;
import com.arto.arto.domain.artwork.type.ShippingMethod;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.orders.type.OrderStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class DummyDataGeneratorTest {

    @Autowired private UsersRepository usersRepo;
    @Autowired private AdminsRepository adminsRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private ArtworkRepository artworkRepo;
    @Autowired private ColorRepository colorRepo;
    @Autowired private SpaceRepository spaceRepo;
    @Autowired private MoodRepository moodRepo;

    @Autowired private OrdersRepository ordersRepo;

    private final Random random = new Random();

    @Test
    @Transactional
    @Rollback(false)  // 핵심: DB에 실제로 남음
    void generateDummyData() {

        /* ========================================
         * 1) 관리자 계정 생성
         * ======================================== */
        UsersEntity adminUser = usersRepo.findByEmail("admin@test.com")
                .orElseGet(() -> usersRepo.save(
                        UsersEntity.builder()
                                .email("admin@test.com")
                                .password(passwordEncoder.encode("Admin1234!"))
                                .name("슈퍼관리자")
                                .address("서울시 용산구 관리자빌딩")
                                .role(Role.ADMIN)
                                .isActive(true)
                                .build()
                ));

        if (adminsRepo.findByUser(adminUser).isEmpty()) {
            adminsRepo.save(AdminsEntity.builder()
                    .user(adminUser)
                    .adminLevel(1)
                    .validUntil(LocalDateTime.now().plusYears(10))
                    .lastActionAt(LocalDateTime.now())
                    .build());
        }

        /* ========================================
         * 2) 구매자 유저 5명 생성
         * ======================================== */
        List<UsersEntity> buyers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final int idx = i;   // 람다에서 사용할 값

            String email = "buyer" + idx + "@test.com";
            UsersEntity user = usersRepo.findByEmail(email)
                    .orElseGet(() -> usersRepo.save(
                            UsersEntity.builder()
                                    .email(email)
                                    .password(passwordEncoder.encode("User1234!"))
                                    .name("구매자" + idx)
                                    .phoneNumber(String.format("010-1111-222%d", idx))
                                    .address("한국 어딘가 " + idx + "번지")
                                    .role(Role.USER)
                                    .isActive(true)
                                    .build()
                    ));
            buyers.add(user);
        }

        /* ========================================
         * 3) 태그 데이터 (색상 / 공간 / 분위기)
         * ======================================== */

        if (colorRepo.count() == 0) {
            colorRepo.saveAll(List.of(
                    new ColorEntity("Red"),
                    new ColorEntity("Blue"),
                    new ColorEntity("Green"),
                    new ColorEntity("Yellow"),
                    new ColorEntity("Black"),
                    new ColorEntity("White")
            ));
        }

        if (spaceRepo.count() == 0) {
            spaceRepo.saveAll(List.of(
                    new SpaceEntity("Living Room"),
                    new SpaceEntity("Bedroom"),
                    new SpaceEntity("Kitchen"),
                    new SpaceEntity("Office"),
                    new SpaceEntity("Hallway")
            ));
        }

        if (moodRepo.count() == 0) {
            moodRepo.saveAll(List.of(
                    new MoodEntity("Modern"),
                    new MoodEntity("Cozy"),
                    new MoodEntity("Vintage"),
                    new MoodEntity("Minimal"),
                    new MoodEntity("Vibrant")
            ));
        }

        List<ColorEntity> colors = colorRepo.findAll();
        List<SpaceEntity> spaces = spaceRepo.findAll();
        List<MoodEntity> moods = moodRepo.findAll();

        /* ========================================
         * 4) 작품 20개 생성
         * ======================================== */

        List<String> titles = List.of(
                "붉은 도시", "고요한 숲", "흑백의 대비", "파란 꿈",
                "초록의 깊이", "황금빛 오후", "차가운 바람", "밤의 미로",
                "빛의 조각", "은하의 흐름", "사막의 숨결", "안개 속의 집",
                "푸른 언덕", "창가의 그림자", "도시의 리듬", "숲의 노래",
                "차분한 오후", "고요한 파도", "빛나는 골목", "새벽의 광장"
        );

        List<String> artists = List.of("김작가", "이작가", "박작가", "최작가", "정작가");

        List<ArtworkEntity> artworks = new ArrayList<>();

        for (int i = 0; i < titles.size(); i++) {

            ArtworkEntity art = ArtworkEntity.builder()
                    .title(titles.get(i))
                    .description("테스트용 작품 설명 (" + titles.get(i) + ")")
                    .artistName(artists.get(random.nextInt(artists.size())))
                    .morph(randomMorph())
                    .status(ArtworkStatus.AVAILABLE)
                    .shippingMethod(ShippingMethod.PARCEL)
                    .price(BigDecimal.valueOf(300000 + random.nextInt(700000)))
                    .shippingCost(BigDecimal.valueOf(3000))
                    .dimensions("50x50cm")
                    .thumbnailImageUrl("https://picsum.photos/300?random=" + (i + 1))
                    .build();

            // ManyToMany 랜덤 매핑
            art.setColors(randomPick(colors, 2, 3));
            art.setSpaces(randomPick(spaces, 1, 2));
            art.setMoods(randomPick(moods, 1, 2));

            artworkRepo.save(art);
            artworks.add(art);
        }

        /* ========================================
         * 5) 주문(Order) 더미 생성
         * ======================================== */

        for (int i = 0; i < 15; i++) {

            UsersEntity buyer = buyers.get(random.nextInt(buyers.size()));
            ArtworkEntity art = artworks.get(random.nextInt(artworks.size()));

            OrdersEntity order = new OrdersEntity();
            order.setBuyer(buyer);
            order.setArtwork(art);
            order.setOrderDate(LocalDate.now().minusDays(random.nextInt(10)));
            order.setTotalAmount(art.getPrice());
            order.setOrderStatus(randomOrderStatus());

            order.setPostCode(12345);
            order.setShippingAddress(buyer.getAddress());
            order.setShippingPhoneNumber(buyer.getPhoneNumber());
            order.setReceiverName(buyer.getName());

            // 배송 상태에 따른 값 설정
            if (order.getOrderStatus() == OrderStatus.SHIPPED ||
                    order.getOrderStatus() == OrderStatus.DELIVERED) {
                order.setDeliveryStartDate(LocalDate.now().minusDays(2));
            }

            if (order.getOrderStatus() == OrderStatus.DELIVERED) {
                order.setDeliveryCompletedDate(LocalDate.now().minusDays(1));
            }

            ordersRepo.save(order);
        }

        System.out.println("🎉 테스트 더미 데이터 생성 완료!");
    }

    /* ------------------------------------
     * 유틸 메서드들
     * ------------------------------------ */

    private Morph randomMorph() {
        Morph[] list = Morph.values();
        return list[random.nextInt(list.length)];
    }

    private OrderStatus randomOrderStatus() {
        OrderStatus[] list = OrderStatus.values();
        return list[random.nextInt(list.length)];
    }

    private <T> List<T> randomPick(List<T> source, int min, int max) {
        int count = random.nextInt(max - min + 1) + min;
        Collections.shuffle(source);
        return new ArrayList<>(source.subList(0, count));
    }
}
