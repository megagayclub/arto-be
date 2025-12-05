package com.arto.arto.domain.artwork.repository;

import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.type.Morph;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.arto.arto.domain.artwork.entity.QArtworkEntity.artworkEntity; // Q파일 static import

@RequiredArgsConstructor
public class ArtworkRepositoryImpl implements ArtworkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtworkEntity> search(ArtworkSearchCondition condition) {
        return queryFactory
                .selectFrom(artworkEntity)
                .where(
                        // 아래 메서드들이 null을 반환하면 조건이 무시됨 (동적 쿼리)
                        keywordContains(condition.getKeyword()),
                        minPriceGoe(condition.getMinPrice()),
                        maxPriceLoe(condition.getMaxPrice()),
                        morphEq(condition.getMorph()),
                        colorsIn(condition.getColors()),
                        spacesIn(condition.getSpaces()),
                        moodsIn(condition.getMoods()),

                        shippingMethodsIn(condition.getShippingMethods()),
                        excludeSoldOut(condition.getExcludeSoldOut())
                )
                .orderBy(artworkEntity.createdAt.desc()) // 최신순 정렬
                .fetch();
    }

    //조건별 필터링 메서드

    // 1. 검색어 (제목 or 작가 이름 포함)
    private BooleanExpression keywordContains(String keyword) {
        return keyword != null ?
                artworkEntity.title.contains(keyword)
                        .or(artworkEntity.artist.name.contains(keyword)) : null;
    }

    // 2. 가격 (최소 가격 이상)
    private BooleanExpression minPriceGoe(BigDecimal minPrice) {
        return minPrice != null ? artworkEntity.price.goe(minPrice) : null;
    }

    // 3. 가격 (최대 가격 이하)
    private BooleanExpression maxPriceLoe(BigDecimal maxPrice) {
        return maxPrice != null ? artworkEntity.price.loe(maxPrice) : null;
    }

    // 4. 형태 (일치)
    private BooleanExpression morphEq(Morph morph) {
        return morph != null ? artworkEntity.morph.eq(morph) : null;
    }

    // 5. 색상 필터 (태그 목록 중 하나라도 포함되면 OK)
    private BooleanExpression colorsIn(List<String> colors) {
        // artwork.colors 안에 있는 이름 중 하나라도 목록에 있으면 통과
        return (colors != null && !colors.isEmpty()) ?
                artworkEntity.colors.any().name.in(colors) : null;
    }

    // 6. 공간 필터
    private BooleanExpression spacesIn(List<String> spaces) {
        return (spaces != null && !spaces.isEmpty()) ?
                artworkEntity.spaces.any().name.in(spaces) : null;
    }

    // 7. 분위기 필터
    private BooleanExpression moodsIn(List<String> moods) {
        return (moods != null && !moods.isEmpty()) ?
                artworkEntity.moods.any().name.in(moods) : null;
    }

    // 8. 배송 방법 필터 (무료배송, 착불 등)
    private BooleanExpression shippingMethodsIn(List<String> shippingMethods) {
        return (shippingMethods != null && !shippingMethods.isEmpty()) ?
                artworkEntity.shippingMethod.stringValue().in(shippingMethods) : null;
    }

    // 9. 품절 제외 필터 (true일 때만 작동)
    private BooleanExpression excludeSoldOut(Boolean excludeSoldOut) {
        // excludeSoldOut이 true이면 -> status가 'SOLD_OUT'이 아닌 것만 가져와라
        return (excludeSoldOut != null && excludeSoldOut) ?
                artworkEntity.status.ne(com.arto.arto.domain.artwork.type.ArtworkStatus.SOLD_OUT) : null;
    }
}