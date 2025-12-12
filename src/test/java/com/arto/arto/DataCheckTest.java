package com.arto.arto;

import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.orders.entity.OrdersEntity;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class DataCheckTest {

    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private ArtworkRepository artworkRepo;

    @Autowired
    private OrdersRepository ordersRepo;

    // ✅ 1) users 상세 출력
    @Test
    void printUsers() {
        List<UsersEntity> users = usersRepo.findAll();
        System.out.println("=== USERS (" + users.size() + ") ===");
        for (UsersEntity u : users) {
            System.out.println(
                    "id=" + u.getUserId()
                            + " | email=" + u.getEmail()
                            + " | name=" + u.getName()
                            + " | role=" + u.getRole()
                            + " | phone=" + u.getPhoneNumber()
                            + " | address=" + u.getAddress()
            );
        }
    }

    // ✅ 2) artworks 상세 출력 (태그까지)
    @Test
    @Transactional  // Lazy 로딩 때문에 걸어두는 게 안전함
    void printArtworks() {
        List<ArtworkEntity> arts = artworkRepo.findAll();
        System.out.println("=== ARTWORKS (" + arts.size() + ") ===");
        for (ArtworkEntity a : arts) {
            System.out.println(
                    "id=" + a.getId()
                            + " | title=" + a.getTitle()
                            + " | artist=" + a.getArtistName()
                            + " | price=" + a.getPrice()
                            + " | morph=" + a.getMorph()
                            + " | status=" + a.getStatus()
                            + " | shipping=" + a.getShippingMethod()
            );
            System.out.println("   colors=" + a.getColors().stream().map(c -> c.getName()).toList());
            System.out.println("   spaces=" + a.getSpaces().stream().map(s -> s.getName()).toList());
            System.out.println("   moods=" + a.getMoods().stream().map(m -> m.getName()).toList());
        }
    }

    // ✅ 3) orders 상세 출력 (원하면)
    @Test
    @Transactional
    void printOrders() {
        List<OrdersEntity> orders = ordersRepo.findAll();
        System.out.println("=== ORDERS (" + orders.size() + ") ===");
        for (OrdersEntity o : orders) {
            System.out.println(
                    "id=" + o.getId()
                            + " | buyer=" + o.getBuyer().getEmail()
                            + " | artwork=" + o.getArtwork().getTitle()
                            + " | status=" + o.getOrderStatus()
                            + " | total=" + o.getTotalAmount()
                            + " | orderDate=" + o.getOrderDate()
            );
        }
    }
}
