package com.arto.arto;

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
import com.arto.arto.domain.cart_items.repository.CartItemsRepository;
import com.arto.arto.domain.inquiries.repository.InquiriesRepository;
import com.arto.arto.domain.orders.repository.OrdersRepository;
import com.arto.arto.domain.wishlists.repository.WishlistsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class ArtworkInitTest {

    @Autowired private ArtworkRepository artworkRepo;
    @Autowired private ColorRepository colorRepo;
    @Autowired private SpaceRepository spaceRepo;
    @Autowired private MoodRepository moodRepo;

    @Autowired private WishlistsRepository wishlistsRepo;
    @Autowired private CartItemsRepository cartItemsRepo;
    @Autowired private OrdersRepository ordersRepo;
    @Autowired private InquiriesRepository inquiriesRepo;

    private final Random random = new Random();

    @Test
    @Transactional
    @Rollback(false)
    void generateArtworksOnly() {
        // 1. 자식 데이터 삭제 (순서 엄수)
        inquiriesRepo.deleteAllInBatch();
        ordersRepo.deleteAllInBatch();
        cartItemsRepo.deleteAllInBatch();
        wishlistsRepo.deleteAllInBatch();
        artworkRepo.deleteAllInBatch();

        // 2. 태그 데이터 보장
        ensureTagsExist();

        List<ColorEntity> colors = colorRepo.findAll();
        List<SpaceEntity> spaces = spaceRepo.findAll();
        List<MoodEntity> moods = moodRepo.findAll();

        // 3. 예술적 단어 소스
        String[] adjectives = {
                "永遠の", "崩れゆく", "静かな", "燦란한", "哀しい", "輝く", "冷たい", "隠された",
                "夢幻的な", "長い", "壊れた", "かすかな", "強烈な", "透明な", "深い", "密かな",
                "見知らぬ", "暖かい", "舞い散る", "止まった", "燃え上がる", "優雅な"
        };
        String[] nouns = {
                "記憶", "孤独", "海", "空", "視선", "欠片", "風", "迷路", "息吹", "夢",
                "波", "丘", "影", "リズム", "歌", "温度", "瞬間", "幻想", "花", "星",
                "対話", "刹那", "未来", "瞑想", "告白", "メロディ", "温もり", "記録", "言葉"
        };
        List<String> artists = List.of(
                "佐藤 健", "田中 美咲", "高橋 蓮", "伊藤 結衣", "渡辺 翔", "中村 陽子",
                "小林 誠", "加藤 恵", "木村 拓也", "斎藤 あすか", "Kenji Murata", "Yuki Sato"
        );

        // 4. 작품 50개 생성
        for (int i = 1; i <= 60; i++) {
            String title = adjectives[random.nextInt(adjectives.length)] + " " +
                    nouns[random.nextInt(nouns.length)];
            String artist = artists.get(random.nextInt(artists.size()));

            ArtworkEntity art = ArtworkEntity.builder()
                    .title(title)
                    .description(String.format("[%s] アーティスト「%s」が贈る美的な体験。この作品は内面と外部の世界が交差する瞬間を捉えています。", title, artist))
                    .artistName(artist)
                    .morph(randomMorph())
                    .status(ArtworkStatus.AVAILABLE)
                    .shippingMethod(ShippingMethod.PARCEL)
                    .price(BigDecimal.valueOf((20 + random.nextInt(131)) * 10000))
                    .shippingCost(BigDecimal.valueOf(3000))
                    .dimensions(String.format("%dx%dcm", (2 + random.nextInt(8)) * 10, (2 + random.nextInt(8)) * 10))
                    .thumbnailImageUrl("https://picsum.photos/600/800?random=" + i)
                    .build();

            // 태그 랜덤 매핑
            art.setColors(randomPick(colors, 1, 3));
            art.setSpaces(randomPick(spaces, 1, 2));
            art.setMoods(randomPick(moods, 1, 2));

            artworkRepo.save(art);
        }

        System.out.println("🎉 모든 연관 데이터 삭제 및 예술적 작품 50개 생성 완료!");
    }

    private void ensureTagsExist() {
        if (colorRepo.count() == 0) {
            List.of("Red", "Blue", "White", "Black", "Gold").forEach(name -> {
                ColorEntity c = new ColorEntity();
                c.setName(name); // 만약 필드명이 다르면 여기서 에러가 날 수 있음 (확인 필요!)
                colorRepo.save(c);
            });
        }
        if (spaceRepo.count() == 0) {
            List.of("Living Room", "Office", "Bedroom").forEach(name -> {
                SpaceEntity s = new SpaceEntity();
                s.setName(name);
                spaceRepo.save(s);
            });
        }
        if (moodRepo.count() == 0) {
            List.of("Modern", "Minimal", "Vintage", "Cozy").forEach(name -> {
                MoodEntity m = new MoodEntity();
                m.setName(name);
                moodRepo.save(m);
            });
        }
    }

    private Morph randomMorph() {
        Morph[] list = Morph.values();
        return list[random.nextInt(list.length)];
    }

    private <T> List<T> randomPick(List<T> source, int min, int max) {
        if (source == null || source.isEmpty()) return new ArrayList<>();
        int count = Math.min(random.nextInt(max - min + 1) + min, source.size());
        List<T> shuffled = new ArrayList<>(source);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, count);
    }
}