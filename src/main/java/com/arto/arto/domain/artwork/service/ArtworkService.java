package com.arto.arto.domain.artwork.service;

import com.arto.arto.domain.artwork.dto.response.ArtworkDetailResponse;
import com.arto.arto.domain.artwork.dto.response.ArtworkSimpleResponse;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.global.common.dto.request.PageRequestDto;
import com.arto.arto.global.common.dto.response.PageResponse;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    public PageResponse<ArtworkSimpleResponse> getArtworks(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.toPageable(); // PageRequest에 이런 메서드 있다고 가정

        Page<ArtworkEntity> page = artworkRepository.findAll(pageable);

        return PageResponse.of(
                page.map(ArtworkSimpleResponse::fromEntity)
        );
    }

    public ArtworkDetailResponse getArtwork(Long id) {
        ArtworkEntity entity = artworkRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException(
                                HttpStatus.NOT_FOUND.value(),        // status
                                "해당 작품을 찾을 수 없습니다."        // message
                        )
                );
        return ArtworkDetailResponse.fromEntity(entity);
    }
}
