package com.arto.arto;

import com.arto.arto.domain.artwork.entity.ColorEntity;
import com.arto.arto.domain.artwork.entity.MoodEntity;
import com.arto.arto.domain.artwork.entity.SpaceEntity;
import com.arto.arto.domain.artwork.repository.ColorRepository;
import com.arto.arto.domain.artwork.repository.MoodRepository;
import com.arto.arto.domain.artwork.repository.SpaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class DataInitTest {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private MoodRepository moodRepository;

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
}