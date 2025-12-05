package com.arto.arto.domain.wishlists.service;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.wishlists.entity.WishlistsEntity;
import com.arto.arto.domain.wishlists.repository.WishlistsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WishlistsService {

    private final WishlistsRepository wishlistsRepository;
    private final UsersRepository usersRepository;
    private final ArtworkRepository artworkRepository;


    @Transactional
    public boolean toggleWishlist(String email, Long artworkId) {
        // 1. 유저 찾기
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 2. 작품 찾기
        ArtworkEntity artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("作品が見つかりません。"));

        // 3. 토글 로직 (있으면 삭제, 없으면 저장)
        if (wishlistsRepository.existsByUserAndArtwork(user, artwork)) {
            wishlistsRepository.deleteByUserAndArtwork(user, artwork);
            return false; // 삭제됨
        } else {
            WishlistsEntity wishlist = WishlistsEntity.builder()
                    .user(user)
                    .artwork(artwork)
                    .addedAt(LocalDateTime.now())
                    .build();
            wishlistsRepository.save(wishlist);
            return true; // 추가됨
        }
    }
}