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
import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.dto.response.ArtworkSimpleResponseDto;
import java.util.stream.Collectors;

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
    public Long createArtwork(String email, ArtworkCreateRequestDto requestDto) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("작가를 찾을 수 없습니다."));

        // 1. 태그 데이터 가져오기
        List<ColorEntity> colors = colorRepository.findAllById(requestDto.getColorIds());
        List<SpaceEntity> spaces = spaceRepository.findAllById(requestDto.getSpaceIds());
        List<MoodEntity> moods = moodRepository.findAllById(requestDto.getMoodIds());

        // 2. 작품 엔티티 생성
        ArtworkEntity artwork = ArtworkEntity.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .artist(user)
                .dimensions(requestDto.getDimensions())
                .shippingCost(requestDto.getShippingCost())
                .thumbnailImageUrl(requestDto.getThumbnailImageUrl())
                .morph(requestDto.getMorph())
                .price(requestDto.getPrice())
                .shippingMethod(requestDto.getShippingMethod())
                .status(ArtworkStatus.AVAILABLE)
                .build();

        ArtworkEntity savedArtwork = artworkRepository.saveAndFlush(artwork);

        savedArtwork.setColors(colors);
        savedArtwork.setSpaces(spaces);
        savedArtwork.setMoods(moods);

        artworkRepository.save(savedArtwork);

        return savedArtwork.getId();
    }

    @Transactional(readOnly = true)
    public ArtworkDetailResponseDto getArtworkDetail(Long artworkId) {
        ArtworkEntity artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("作品が見つかりません。")); // 작품을 찾을 수 없습니다.

        return ArtworkDetailResponseDto.fromEntity(artwork);
    }

    @Transactional(readOnly = true)
    public List<ArtworkSimpleResponseDto> getArtworkList(ArtworkSearchCondition condition) {
        // 1. QueryDSL로 조건에 맞는 작품들 찾아오기
        List<ArtworkEntity> artworks = artworkRepository.search(condition);

        // 2. Entity 리스트를 -> DTO 리스트로 변환 (요약 정보만)
        return artworks.stream()
                .map(ArtworkSimpleResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


}