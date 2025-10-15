package com.arto.arto.domain.artwork.entity;

import com.arto.arto.domain.artwork.type.Mood;
import com.arto.arto.domain.artwork.type.Morph;
import com.arto.arto.domain.artwork.type.ShippingMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_artworks")
public class ArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_id")
    private Long id;

    @Column(name = "registered_by_admin_id", nullable = false)
    private Long registeredByAdminId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "creation_year")
    private String creationYear;

    @Column
    private String dimension;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Morph morph;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    private ShippingMethod shippingMethod;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "inquiry_count")
    private Long inquiryCount = 0L;

    // 기본 생성자
    public ArtworkEntity() {}

    // Getters & Setters (Lombok 추천 가능)
}
