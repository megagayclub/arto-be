package com.arto.arto.domain.artwork.entity;

import com.arto.arto.domain.artwork.type.ArtworkStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artworks")
public class ArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_id")
    private Long id;

    @Column(name = "registered_by_admin_id")
    private Long registeredByAdminId;

    @Column(nullable = false)
    private String title;

    @Column(name = "short_description", length = 1000)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "creation_year")
    private Integer creationYear;

    @Column
    private String dimensions;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtworkStatus status;

    @Column(name = "frame_type")
    private String frameType;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "thumbnail_image_url", nullable = false)
    private String thumbnailImageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "inquiry_count")
    private int inquiryCount = 0;

    @Column
    private String morph;

    // 색상 / 공간 / 분위기 매핑
    @ManyToMany
    @JoinTable(
            name = "tbl_artwork_colors",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private Set<ColorEntity> colors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_artwork_spaces",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "space_id")
    )
    private Set<SpaceEntity> spaces = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_artwork_moods",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    private Set<MoodEntity> moods = new HashSet<>();

    // 기본 생성자
    public ArtworkEntity() {}
}
