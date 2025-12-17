package com.arto.arto.domain.artwork_images.repository;

import com.arto.arto.domain.artwork_images.entity.ArtworkImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkImagesRepository extends JpaRepository<ArtworkImagesEntity, Long> {
}