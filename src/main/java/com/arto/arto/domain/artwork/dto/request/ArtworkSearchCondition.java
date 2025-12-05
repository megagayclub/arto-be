package com.arto.arto.domain.artwork.dto.request;

import com.arto.arto.domain.artwork.type.Morph;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ArtworkSearchCondition {

    //헤어 검색용 (작품명, 작가명)
    private String keyword;

    //상세 검색용
    private List<String> colors;
    private List<String> spaces;
    private List<String> moods;
    private Morph morph;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    //배송 방법 (무료배송, 착불 등)
    private List<String> shippingMethods;

    //품절 제외 여부 (체크하면 true)
    private Boolean excludeSoldOut;
}