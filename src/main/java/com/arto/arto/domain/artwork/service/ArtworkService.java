package com.arto.arto.domain.artwork.service;

import com.arto.arto.domain.artwork.dto.request.ArtworkCreateRequestDto;
import com.arto.arto.domain.artwork.dto.response.ArtworkDetailResponseDto;
import com.arto.arto.domain.artwork.entity.*;
import com.arto.arto.domain.artwork.repository.*;
import com.arto.arto.domain.artwork.type.ArtworkStatus;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final UsersRepository usersRepository;
    private final ColorRepository colorRepository;
    private final SpaceRepository spaceRepository;
    private final MoodRepository moodRepository;

    @Transactional
    public Long createArtwork(String userEmail, ArtworkCreateRequestDto requestDto) {
        // 1. 작가(로그인한 유저) 찾기
        UsersEntity artist = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。")); // 사용자를 찾을 수 없습니다.

        // 2. 태그들(Color, Space, Mood) ID로 찾아오기
        // (ID가 없거나 null이면 빈 리스트 반환)
        List<ColorEntity> colors = requestDto.getColorIds() != null ?
                colorRepository.findAllById(requestDto.getColorIds()) : List.of();

        List<SpaceEntity> spaces = requestDto.getSpaceIds() != null ?
                spaceRepository.findAllById(requestDto.getSpaceIds()) : List.of();

        List<MoodEntity> moods = requestDto.getMoodIds() != null ?
                moodRepository.findAllById(requestDto.getMoodIds()) : List.of();

        // 3. 엔티티 생성
        ArtworkEntity artwork = ArtworkEntity.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .morph(requestDto.getMorph())
                .shippingMethod(requestDto.getShippingMethod())
                .price(requestDto.getPrice())
                .shippingCost(requestDto.getShippingCost())
                .dimensions(requestDto.getDimensions())
                .thumbnailImageUrl(requestDto.getThumbnailImageUrl())
                .status(ArtworkStatus.AVAILABLE) // 기본 상태: 판매 중
                .artist(artist) // 작가 연결
                .colors(new HashSet<>(colors)) // 태그 연결
                .spaces(new HashSet<>(spaces))
                .moods(new HashSet<>(moods))
                .build();

        // 4. 저장
        return artworkRepository.save(artwork).getId();
    }

    @Transactional(readOnly = true)
    public ArtworkDetailResponseDto getArtworkDetail(Long artworkId) {
        ArtworkEntity artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("作品が見つかりません。")); // 작품을 찾을 수 없습니다.

        return ArtworkDetailResponseDto.fromEntity(artwork);
    }
}