package com.arto.arto.domain.artwork.repository;
import com.arto.arto.domain.artwork.entity.SpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {
}