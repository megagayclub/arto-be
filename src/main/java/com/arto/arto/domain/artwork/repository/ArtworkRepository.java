package com.arto.arto.domain.artwork.repository;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    // 나중에 제목 검색, 카테고리 검색 같은 메서드 추가 가능
    // List<ArtworkEntity> findByStatus(ArtworkStatus status);
}
