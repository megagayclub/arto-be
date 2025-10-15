package com.arto.arto.domain.wishlists.entity;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_wishlists")  // 복수형 테이블명 명시
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;  // ✅ users 테이블과 연결

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artwork_id", nullable = false)
    private ArtworkEntity artwork;  // ✅ artworks 테이블과 연결

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;
}
