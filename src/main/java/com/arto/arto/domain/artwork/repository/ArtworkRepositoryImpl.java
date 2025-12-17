package com.arto.arto.domain.artwork.repository;

import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.type.Morph;
import com.querydsl.core.types.OrderSpecifier;
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
        System.out.println("검색 키워드 확인: " + condition.getKeyword());
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
                .orderBy(getSortOrder(condition.getSort())) // 정렬 조건 적용
                .fetch();
    }

    //조건별 필터링 메서드

    // 1. 검색어 (제목 or 작가 이름 포함)
    private BooleanExpression keywordContains(String keyword) {
        return keyword != null ?
                artworkEntity.title.contains(keyword) // 제목에 포함되거나
                        .or(artworkEntity.artistName.contains(keyword)) : null; // 작가 이름에 포함되면 OK
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

    // 5. 색상 필터
    private BooleanExpression colorsIn(List<Long> colors) { // String -> Long
        return (colors != null && !colors.isEmpty()) ?
                artworkEntity.colors.any().id.in(colors) : null; // name -> id
    }

    // 6. 공간 필터
    private BooleanExpression spacesIn(List<Long> spaces) { // String -> Long
        return (spaces != null && !spaces.isEmpty()) ?
                artworkEntity.spaces.any().id.in(spaces) : null; // name -> id
    }

    // 7. 분위기 필터
    private BooleanExpression moodsIn(List<Long> moods) { // String -> Long
        return (moods != null && !moods.isEmpty()) ?
                artworkEntity.moods.any().id.in(moods) : null; // name -> id
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

    private OrderSpecifier<?> getSortOrder(String sort) {
        if (sort == null) {
            return artworkEntity.createdAt.desc(); // 기본값: 최신순
        }

        return switch (sort) {
            case "PRICE_ASC" -> artworkEntity.price.asc();    // 가격 낮은 순
            case "PRICE_DESC" -> artworkEntity.price.desc();  // 가격 높은 순
            default -> artworkEntity.createdAt.desc();        // 그 외엔 최신순
        };
    }
}