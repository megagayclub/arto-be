package com.arto.arto.domain.wishlists.repository;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.wishlists.entity.WishlistsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistsRepository extends JpaRepository<WishlistsEntity, Long> {

    // 1. 이미 찜했는지 확인
    boolean existsByUserAndArtwork(UsersEntity user, ArtworkEntity artwork);

    // 2. 찜 취소 (삭제)
    void deleteByUserAndArtwork(UsersEntity user, ArtworkEntity artwork);

    List<WishlistsEntity> findAllByUserOrderByAddedAtDesc(UsersEntity user);
}