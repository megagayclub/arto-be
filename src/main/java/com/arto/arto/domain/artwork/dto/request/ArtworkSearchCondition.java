package com.arto.arto.domain.artwork.dto.request;

import com.arto.arto.domain.artwork.type.Morph;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ArtworkSearchCondition {

    //헤어 검색용 (작품명, 작가명)
    private String keyword;

    //상세 검색용
    private List<Long> spaces;
    private List<Long> colors;
    private List<Long> moods;
    private Morph morph;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    //배송 방법 (무료배송, 착불 등)
    private List<String> shippingMethods;

    //품절 제외 여부 (체크하면 true)
    private Boolean excludeSoldOut;

    // "PRICE_ASC" (싼순), "PRICE_DESC" (비싼순), "LATEST" (최신순)
    private String sort;
}