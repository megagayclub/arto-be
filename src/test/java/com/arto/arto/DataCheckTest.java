package com.arto.arto;

import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.admins.repository.AdminsRepository;

import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;

import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // 🔥 테스트 전용 설정 적용
public class DataCheckTest {

    @Autowired
    private UsersRepository usersRepo;
    @Autowired
    private AdminsRepository adminsRepo;
    @Autowired
    private ArtworkRepository artworkRepo;
    @Autowired
    private OrdersRepository ordersRepo;

    /* =============================
     *  USERS 조회
     * ============================= */
    @Test
    @Transactional
    @Rollback(false)
    void printUsers() {
        List<UsersEntity> users = usersRepo.findAll();
        System.out.println("\n========== USERS (" + users.size() + ") ==========");

        for (UsersEntity u : users) {
            System.out.println("-------------------------------------");
            System.out.println("ID           : " + u.getUserId());
            System.out.println("Email        : " + u.getEmail());
            System.out.println("Name         : " + u.getName());
            System.out.println("Password     : " + u.getPassword());
            System.out.println("PhoneNumber  : " + u.getPhoneNumber());
            System.out.println("Address      : " + u.getAddress());
            System.out.println("Role         : " + u.getRole());
            System.out.println("Active       : " + u.isActive());
            System.out.println("LastLoginAt  : " + u.getLastLoginAt());
            System.out.println("Memo         : " + u.getMemo());
        }
    }

    /* =============================
     *  ADMINS 조회
     * ============================= */
    @Test
    @Transactional
    @Rollback(false)
    void printAdmins() {
        List<AdminsEntity> list = adminsRepo.findAll();
        System.out.println("\n========== ADMINS (" + list.size() + ") ==========");

        for (AdminsEntity a : list) {
            System.out.println("-------------------------------------");
            System.out.println("AdminID      : " + a.getAdminId());
            System.out.println("UserID       : " + a.getUser().getUserId());
            System.out.println("AdminLevel   : " + a.getAdminLevel());
            System.out.println("ValidUntil   : " + a.getValidUntil());
            System.out.println("LastActionAt : " + a.getLastActionAt());
        }
    }

    /* =============================
     *  ARTWORKS 조회
     * ============================= */
    @Test
    @Transactional
    @Rollback(false)
    void printArtworks() {
        List<ArtworkEntity> arts = artworkRepo.findAll();

        System.out.println("\n========== ARTWORKS (" + arts.size() + ") ==========");

        int idx = 1;
        for (ArtworkEntity a : arts) {
            System.out.println("\n[" + idx++ + "] ------------------------------");
            System.out.println("Artwork ID        : " + a.getId());
            System.out.println("Title             : " + a.getTitle());
            System.out.println("Description       : " + a.getDescription());
            System.out.println("Artist Name       : " + a.getArtistName());
            System.out.println("Morph             : " + a.getMorph());
            System.out.println("Status            : " + a.getStatus());
            System.out.println("Shipping Method   : " + a.getShippingMethod());
            System.out.println("Price             : " + a.getPrice());
            System.out.println("Shipping Cost     : " + a.getShippingCost());
            System.out.println("Dimensions        : " + a.getDimensions());
            System.out.println("Thumbnail URL     : " + a.getThumbnailImageUrl());
            System.out.println("Created At        : " + a.getCreatedAt());
            System.out.println("Updated At        : " + a.getUpdatedAt());

            // ManyToMany - Colors
            System.out.print("Colors            : ");
            a.getColors().forEach(c -> System.out.print(c.getName() + " "));
            System.out.println();

            // Spaces
            System.out.print("Spaces            : ");
            a.getSpaces().forEach(s -> System.out.print(s.getName() + " "));
            System.out.println();

            // Moods
            System.out.print("Moods             : ");
            a.getMoods().forEach(m -> System.out.print(m.getName() + " "));
            System.out.println();
        }
    }


    /* =============================
     *  ORDERS 조회
     * ============================= */
    @Test
    @Transactional
    @Rollback(false)
    void printOrders() {
        List<OrdersEntity> orders = ordersRepo.findAll();

        System.out.println("\n========== ORDERS (" + orders.size() + ") ==========");

        for (OrdersEntity o : orders) {
            System.out.println("Order ID             : " + o.getId());

            // Buyer 정보
            UsersEntity buyer = o.getBuyer();
            System.out.println("Buyer ID             : " + buyer.getUserId());
            System.out.println("Buyer Name           : " + buyer.getName());
            System.out.println("Buyer Email          : " + buyer.getEmail());
            System.out.println("Buyer Phone          : " + buyer.getPhoneNumber());

            // Artwork 정보
            ArtworkEntity art = o.getArtwork();
            System.out.println("Artwork ID           : " + art.getId());
            System.out.println("Artwork Title        : " + art.getTitle());
            System.out.println("Artwork Artist       : " + art.getArtistName());
            System.out.println("Artwork Price        : " + art.getPrice());

            // 주문 필드들
            System.out.println("Order Date           : " + o.getOrderDate());
            System.out.println("Total Amount         : " + o.getTotalAmount());
            System.out.println("Order Status         : " + o.getOrderStatus());
            System.out.println("Post Code            : " + o.getPostCode());
            System.out.println("Shipping Address     : " + o.getShippingAddress());
            System.out.println("Shipping Phone       : " + o.getShippingPhoneNumber());
            System.out.println("Receiver Name        : " + o.getReceiverName());
            System.out.println("Delivery Start       : " + o.getDeliveryStartDate());
            System.out.println("Delivery Completed   : " + o.getDeliveryCompletedDate());
            System.out.println("Shipping Carrier     : " + o.getShippingCarrier());
            System.out.println("Tracking Number      : " + o.getTrackingNumber());
        }
    }
}
