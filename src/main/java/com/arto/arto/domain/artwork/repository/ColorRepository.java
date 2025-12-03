package com.arto.arto.domain.artwork.repository;
import com.arto.arto.domain.artwork.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
}