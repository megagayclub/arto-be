package com.arto.arto.domain.cart.dto.response;

import com.arto.arto.domain.cart_items.entity.CartItemsEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartItemResponse {

    private Long cartItemId;
    private Long artworkId;
    private String title;
    private String artistName;
    private BigDecimal price;
    private String thumbnailImageUrl;

    public static CartItemResponse fromEntity(CartItemsEntity entity) {
        var artwork = entity.getArtwork();

        return CartItemResponse.builder()
                .cartItemId(entity.getId())
                .artworkId(artwork.getId())
                .title(artwork.getTitle())
                .artistName(artwork.getArtistName())
                .price(artwork.getPrice())
                .thumbnailImageUrl(artwork.getThumbnailImageUrl())
                .build();
    }

    public BigDecimal getSubtotal() {
        // 수량 개념이 없으니, 그냥 가격 = 합계
        return price;
    }
}
