package com.arto.arto.domain.artwork.dto.response;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.entity.ColorEntity;
import com.arto.arto.domain.artwork.entity.MoodEntity;
import com.arto.arto.domain.artwork.entity.SpaceEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ArtworkDetailResponseDto {

    private Long artworkId;
    private String title;
    private String description;

    //작가 이름은 User 엔티티에서 가져옵니다.
    private String artistName;
    private Long artistId; // 작가 ID도 주면 프론트에서 편합니다.

    //Enum 값들은 String으로 변환해서 내보냅니다.
    private String morph;          // 형태
    private String status;         // 판매 상태
    private String shippingMethod; // 배송 방식

    private BigDecimal price;
    private BigDecimal shippingCost;
    private String dimensions;
    private String thumbnailImageUrl;

    //태그 정보들 (이름 목록으로 반환)
    private List<String> colors;
    private List<String> spaces;
    private List<String> moods;

    private int viewCount;    // (엔티티에 없으면 0으로 고정하거나 엔티티에 추가 필요)
    private int inquiryCount; // (엔티티에 없으면 0으로 고정)

    public static ArtworkDetailResponseDto fromEntity(ArtworkEntity entity) {
        return ArtworkDetailResponseDto.builder()
                .artworkId(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())

                //작가 정보 추출
                .artistName(entity.getArtist().getName())
                .artistId(entity.getArtist().getUserId())

                //Enum -> String 변환 (.name())
                .morph(entity.getMorph().name())
                .status(entity.getStatus().name())
                .shippingMethod(entity.getShippingMethod().name())

                .price(entity.getPrice())
                .shippingCost(entity.getShippingCost())
                .dimensions(entity.getDimensions())
                .thumbnailImageUrl(entity.getThumbnailImageUrl())

                //태그 정보 추출 (Entity Set -> String List 변환)
                .colors(entity.getColors().stream().map(ColorEntity::getName).collect(Collectors.toList()))
                .spaces(entity.getSpaces().stream().map(SpaceEntity::getName).collect(Collectors.toList()))
                .moods(entity.getMoods().stream().map(MoodEntity::getName).collect(Collectors.toList()))

                // (임시) 조회수는 아직 엔티티에 없으므로 0 처리 (나중에 엔티티에 추가하면 get으로 변경)
                .viewCount(0)
                .inquiryCount(0)
                .build();
    }
}