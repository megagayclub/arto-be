package com.arto.arto.domain.artwork.repository;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long>, ArtworkRepositoryCustom {
}