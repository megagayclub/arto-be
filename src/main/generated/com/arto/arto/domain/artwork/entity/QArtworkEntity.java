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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtworkEntity artworkEntity = new QArtworkEntity("artworkEntity");

    public final com.arto.arto.domain.users.entity.QUsersEntity artist;

    public final SetPath<ColorEntity, QColorEntity> colors = this.<ColorEntity, QColorEntity>createSet("colors", ColorEntity.class, QColorEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath dimensions = createString("dimensions");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<MoodEntity, QMoodEntity> moods = this.<MoodEntity, QMoodEntity>createSet("moods", MoodEntity.class, QMoodEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.arto.arto.domain.artwork.type.Morph> morph = createEnum("morph", com.arto.arto.domain.artwork.type.Morph.class);

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> shippingCost = createNumber("shippingCost", java.math.BigDecimal.class);

    public final EnumPath<com.arto.arto.domain.artwork.type.ShippingMethod> shippingMethod = createEnum("shippingMethod", com.arto.arto.domain.artwork.type.ShippingMethod.class);

    public final SetPath<SpaceEntity, QSpaceEntity> spaces = this.<SpaceEntity, QSpaceEntity>createSet("spaces", SpaceEntity.class, QSpaceEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.arto.arto.domain.artwork.type.ArtworkStatus> status = createEnum("status", com.arto.arto.domain.artwork.type.ArtworkStatus.class);

    public final StringPath thumbnailImageUrl = createString("thumbnailImageUrl");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QArtworkEntity(String variable) {
        this(ArtworkEntity.class, forVariable(variable), INITS);
    }

    public QArtworkEntity(Path<? extends ArtworkEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtworkEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtworkEntity(PathMetadata metadata, PathInits inits) {
        this(ArtworkEntity.class, metadata, inits);
    }

    public QArtworkEntity(Class<? extends ArtworkEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("artist")) : null;
    }

}

