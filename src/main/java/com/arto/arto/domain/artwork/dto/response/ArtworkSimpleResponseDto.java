package com.arto.arto.domain.artwork.dto.response;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ArtworkSimpleResponseDto {

    private Long artworkId;
    private String title;
    private String artistName;
    private BigDecimal price;
    private String thumbnailImageUrl;

    public static ArtworkSimpleResponseDto fromEntity(ArtworkEntity entity) {
        return ArtworkSimpleResponseDto.builder()
                .artworkId(entity.getId())
                .title(entity.getTitle())

                //작가 객체(getArtist)에서 이름(getName)을 꺼냅니다.
                .artistName(entity.getArtistName())

                .price(entity.getPrice())
                .thumbnailImageUrl(entity.getThumbnailImageUrl())
                .build();
    }
}