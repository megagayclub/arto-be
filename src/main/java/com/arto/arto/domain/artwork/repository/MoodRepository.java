package com.arto.arto.domain.artwork.repository;
import com.arto.arto.domain.artwork.entity.MoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<MoodEntity, Long> {
}