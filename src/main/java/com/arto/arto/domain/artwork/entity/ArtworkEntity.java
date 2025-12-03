package com.arto.arto.domain.artwork.entity;

import com.arto.arto.domain.artwork.type.ArtworkStatus;
import com.arto.arto.domain.artwork.type.Morph;
import com.arto.arto.domain.artwork.type.ShippingMethod;
import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_artworks")
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 기록
public class ArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    //중요: String이 아니라 Enum으로 변경했습니다!
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Morph morph; // 형태 (원형, 사각형 등)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtworkStatus status; // 판매 상태

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method")
    private ShippingMethod shippingMethod; // 배송 방식

    @Column(nullable = false)
    private BigDecimal price; // 작품 가격

    @Column(name = "shipping_cost")
    private BigDecimal shippingCost; // 배송비

    @Column(nullable = false)
    private String dimensions; // 크기 (예: 50x50cm)

    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    //작가 정보 (User 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private UsersEntity artist;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    //태그 정보 연결 (색상, 공간, 분위기)
    @ManyToMany
    @JoinTable(
            name = "tbl_artwork_colors",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    @Builder.Default // 빌더 패턴 사용 시 초기화 유지
    private Set<ColorEntity> colors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_artwork_spaces",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "space_id")
    )
    @Builder.Default
    private Set<SpaceEntity> spaces = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_artwork_moods",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    @Builder.Default
    private Set<MoodEntity> moods = new HashSet<>();
}