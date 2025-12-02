package com.arto.arto.domain.artwork.dto.response;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ArtworkDetailResponse {

    private Long artworkId;
    private String title;
    private String artistName;
    private String shortDescription;
    private String description;
    private Integer creationYear;
    private String dimensions;
    private BigDecimal price;
    private String status;
    private String frameType;
    private String shippingMethod;
    private BigDecimal shippingCost;
    private String thumbnailImageUrl;
    private int viewCount;
    private int inquiryCount;

    public static ArtworkDetailResponse fromEntity(ArtworkEntity entity) {
        return ArtworkDetailResponse.builder()
                .artworkId(entity.getId())
                .title(entity.getTitle())
                .artistName(entity.getArtistName())
                .shortDescription(entity.getShortDescription())
                .description(entity.getDescription())
                .creationYear(entity.getCreationYear())
                .dimensions(entity.getDimensions())
                .price(entity.getPrice())
                .status(entity.getStatus().name())
                .frameType(entity.getFrameType())
                .shippingMethod(entity.getShippingMethod())
                .shippingCost(entity.getShippingCost())
                .thumbnailImageUrl(entity.getThumbnailImageUrl())
                .viewCount(entity.getViewCount())
                .inquiryCount(entity.getInquiryCount())
                .build();
    }
}
