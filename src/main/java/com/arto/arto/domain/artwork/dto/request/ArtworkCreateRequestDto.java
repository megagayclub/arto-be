package com.arto.arto.domain.artwork.dto.request;

import com.arto.arto.domain.artwork.type.Morph;
import com.arto.arto.domain.artwork.type.ShippingMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArtworkCreateRequestDto {

    @NotBlank(message = "作品名は必須です。") // 작품명은 필수입니다.
    private String title;

    private String description;

    @NotNull(message = "形態は必須です。") // 형태는 필수입니다.
    private Morph morph; // ENUM

    @NotNull(message = "配送方法は必須です。") // 배송 방식은 필수입니다.
    private ShippingMethod shippingMethod;

    @NotNull(message = "価格は必須です。") // 가격은 필수입니다.
    private BigDecimal price;

    private BigDecimal shippingCost;

    @NotBlank(message = "サイズ情報は必須です。") // 크기 정보는 필수입니다.
    private String dimensions;

    private String thumbnailImageUrl;

    // 태그 ID 목록
    private List<Long> colorIds;
    private List<Long> spaceIds;
    private List<Long> moodIds;
}