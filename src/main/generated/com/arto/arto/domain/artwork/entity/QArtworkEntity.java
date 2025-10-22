package com.arto.arto.domain.artwork.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtworkEntity is a Querydsl query type for ArtworkEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtworkEntity extends EntityPathBase<ArtworkEntity> {

    private static final long serialVersionUID = 1446249923L;

    public static final QArtworkEntity artworkEntity = new QArtworkEntity("artworkEntity");

    public final StringPath artistName = createString("artistName");

    public final SetPath<ColorEntity, QColorEntity> colors = this.<ColorEntity, QColorEntity>createSet("colors", ColorEntity.class, QColorEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> creationYear = createNumber("creationYear", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dimensions = createString("dimensions");

    public final StringPath frameType = createString("frameType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> inquiryCount = createNumber("inquiryCount", Integer.class);

    public final SetPath<MoodEntity, QMoodEntity> moods = this.<MoodEntity, QMoodEntity>createSet("moods", MoodEntity.class, QMoodEntity.class, PathInits.DIRECT2);

    public final StringPath morph = createString("morph");

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final NumberPath<Long> registeredByAdminId = createNumber("registeredByAdminId", Long.class);

    public final NumberPath<java.math.BigDecimal> shippingCost = createNumber("shippingCost", java.math.BigDecimal.class);

    public final StringPath shippingMethod = createString("shippingMethod");

    public final StringPath shortDescription = createString("shortDescription");

    public final SetPath<SpaceEntity, QSpaceEntity> spaces = this.<SpaceEntity, QSpaceEntity>createSet("spaces", SpaceEntity.class, QSpaceEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.arto.arto.domain.artwork.type.ArtworkStatus> status = createEnum("status", com.arto.arto.domain.artwork.type.ArtworkStatus.class);

    public final StringPath thumbnailImageUrl = createString("thumbnailImageUrl");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QArtworkEntity(String variable) {
        super(ArtworkEntity.class, forVariable(variable));
    }

    public QArtworkEntity(Path<? extends ArtworkEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArtworkEntity(PathMetadata metadata) {
        super(ArtworkEntity.class, metadata);
    }

}

