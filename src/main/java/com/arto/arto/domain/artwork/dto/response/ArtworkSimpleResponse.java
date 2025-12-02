package com.arto.arto.domain.artwork.dto.response;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ArtworkSimpleResponse {

    private Long artworkId;
    private String title;
    private String artistName;
    private BigDecimal price;
    private String thumbnailImageUrl;

    public static ArtworkSimpleResponse fromEntity(ArtworkEntity entity) {
        return ArtworkSimpleResponse.builder()
                .artworkId(entity.getId())
                .title(entity.getTitle())
                .artistName(entity.getArtistName())
                .price(entity.getPrice())
                .thumbnailImageUrl(entity.getThumbnailImageUrl())
                .build();
    }
}
