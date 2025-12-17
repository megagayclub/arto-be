package com.arto.arto;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.entity.ColorEntity;
import com.arto.arto.domain.artwork.entity.MoodEntity;
import com.arto.arto.domain.artwork.entity.SpaceEntity;
import com.arto.arto.domain.artwork.repository.*;
import com.arto.arto.domain.artwork.type.ArtworkStatus;
import com.arto.arto.domain.artwork.type.Morph;
import com.arto.arto.domain.artwork.type.ShippingMethod;
import com.arto.arto.domain.artwork_images.entity.ArtworkImagesEntity;
import com.arto.arto.domain.artwork_images.repository.ArtworkImagesRepository;
import com.arto.arto.domain.artwork_images.type.ImageType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class initArtworkData {

    @Autowired private ArtworkRepository artworkRepository;
    @Autowired private ArtworkImagesRepository artworkImagesRepository;
    @Autowired private ColorRepository colorRepository;
    @Autowired private SpaceRepository spaceRepository;
    @Autowired private MoodRepository moodRepository;

    @Test
    @Transactional
    @Rollback(false)
    void initArtworkData() {
        // 1. 태그 기초 데이터 가져오기
        List<ColorEntity> colorList = colorRepository.findAll();
        List<SpaceEntity> spaceList = spaceRepository.findAll();
        List<MoodEntity> moodList = moodRepository.findAll();

        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            // 2. 작품 빌더로 생성
            ArtworkEntity artwork = ArtworkEntity.builder()
                    .title("페페의 마스터피스 No." + i)
                    .artistName("작가 페페 " + (i % 5 + 1))
                    .description("이 작품은 페페의 예술적 영감을 담은 " + i + "번째 걸작입니다.")
                    .price(BigDecimal.valueOf(100000 + (random.nextInt(10) * 50000)))
                    .shippingCost(BigDecimal.valueOf(3000))
                    .dimensions("50x50cm")
                    .morph(Morph.values()[random.nextInt(Morph.values().length)])
                    .status(ArtworkStatus.AVAILABLE)
                    .shippingMethod(ShippingMethod.FREE_SHIPPING)
                    .thumbnailImageUrl("https://arto-s3.s3.ap-northeast-2.amazonaws.com/pepe.jpg")
                    .images(new ArrayList<>()) // 리스트 초기화
                    .build();

            // 3. 이미지 정보 생성 및 연관관계 편의 메서드 활용
            ArtworkImagesEntity image = new ArtworkImagesEntity();
            image.setArtwork(artwork); // 연관관계 설정
            image.setImageUrl("https://arto-s3.s3.ap-northeast-2.amazonaws.com/pepe.jpg");
            image.setImageType(ImageType.THUMBNAIL);
            image.setOrderIndex(0);
            image.setUploadedAt(LocalDateTime.now());

            artwork.getImages().add(image); // ArtworkEntity의 cascade 덕분에 같이 저장됨

            // 4. 랜덤 태그 설정 (세터 활용)
            if (!colorList.isEmpty()) {
                artwork.setColors(List.of(colorList.get(random.nextInt(colorList.size()))));
            }
            if (!spaceList.isEmpty()) {
                artwork.setSpaces(List.of(spaceList.get(random.nextInt(spaceList.size()))));
            }
            if (!moodList.isEmpty()) {
                artwork.setMoods(List.of(moodList.get(random.nextInt(moodList.size()))));
            }

            // 5. 최종 저장
            artworkRepository.save(artwork);
        }

        System.out.println("==========================================");
        System.out.println("페페 작품 20개 저장 완료 (Cascade 활용)");
        System.out.println("==========================================");
    }
}