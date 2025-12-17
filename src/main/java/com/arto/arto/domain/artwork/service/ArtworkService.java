package com.arto.arto.domain.artwork.service;

import com.arto.arto.domain.artwork.dto.request.ArtworkCreateRequestDto;
import com.arto.arto.domain.artwork.dto.response.ArtworkDetailResponseDto;
import com.arto.arto.domain.artwork.entity.*;
import com.arto.arto.domain.artwork.repository.*;
import com.arto.arto.domain.artwork.type.ArtworkStatus;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.global.util.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.dto.response.ArtworkSimpleResponseDto;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final UsersRepository usersRepository;
    private final ColorRepository colorRepository;
    private final SpaceRepository spaceRepository;
    private final MoodRepository moodRepository;
    private final S3UploaderService s3UploaderService;

    @Transactional
    public Long createArtwork(String email, ArtworkCreateRequestDto requestDto, MultipartFile file) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("작가를 찾을 수 없습니다."));

        // 1. S3에 이미지 업로드 및 URL 획득
        String uploadedImageUrl = s3UploaderService.uploadFile(file, "artworks");

        // 2. 태그 데이터 가져오기
        List<ColorEntity> colors = colorRepository.findAllById(requestDto.getColorIds());
        List<SpaceEntity> spaces = spaceRepository.findAllById(requestDto.getSpaceIds());
        List<MoodEntity> moods = moodRepository.findAllById(requestDto.getMoodIds());

        // 3. 작품 엔티티 생성 (불필요한 기호 제거 및 빌더 연결)
        ArtworkEntity artwork = ArtworkEntity.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .artistName(requestDto.getArtistName())
                .dimensions(requestDto.getDimensions())
                .shippingCost(requestDto.getShippingCost())
                .thumbnailImageUrl(uploadedImageUrl) // S3 URL 할당
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
                .orElseThrow(() -> new IllegalArgumentException("作品が見つかりません。"));

        return ArtworkDetailResponseDto.fromEntity(artwork);
    }

    @Transactional(readOnly = true)
    public List<ArtworkSimpleResponseDto> getArtworkList(ArtworkSearchCondition condition) {
        List<ArtworkEntity> artworks = artworkRepository.search(condition);

        return artworks.stream()
                .map(ArtworkSimpleResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}