package com.arto.arto.domain.wishlists.dto.response;

import com.arto.arto.domain.wishlists.entity.WishlistsEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WishlistResponseDto {

    private Long wishlistId;
    private Long artworkId;
    private String title;           // 작품 제목
    private String artistName;      // 작가 이름
    private String thumbnailImageUrl; // 썸네일
    private Integer price;          // 가격
    private LocalDateTime addedAt;  // 찜한 날짜

    public static WishlistResponseDto fromEntity(WishlistsEntity entity) {
        return WishlistResponseDto.builder()
                .wishlistId(entity.getWishlistId())
                .artworkId(entity.getArtwork().getId()) // 작품 ID
                .title(entity.getArtwork().getTitle())         // 작품 제목
                // 작가가 null일 수도 있으니 안전하게 처리 (옵션)
                .artistName(entity.getArtwork().getArtistName() != null ? entity.getArtwork().getArtistName() : "Unknown")
                .thumbnailImageUrl(entity.getArtwork().getThumbnailImageUrl())
                // BigDecimal이라면 .intValue(), Integer라면 그대로
                .price(entity.getArtwork().getPrice().intValue())
                .addedAt(entity.getAddedAt())
                .build();
    }
}