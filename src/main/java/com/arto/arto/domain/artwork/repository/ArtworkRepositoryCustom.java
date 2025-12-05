package com.arto.arto.domain.artwork.repository;

import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import java.util.List;

public interface ArtworkRepositoryCustom {
    // 조건을 받아서 작품 목록(List)을 반환하는 메서드
    List<ArtworkEntity> search(ArtworkSearchCondition condition);
}