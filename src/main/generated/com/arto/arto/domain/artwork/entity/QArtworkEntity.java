package com.arto.arto.domain.artwork.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArtworkEntity is a Querydsl query type for ArtworkEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtworkEntity extends EntityPathBase<ArtworkEntity> {

    private static final long serialVersionUID = 1446249923L;

    public static final QArtworkEntity artworkEntity = new QArtworkEntity("artworkEntity");

    public final StringPath artistName = createString("artistName");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath creationYear = createString("creationYear");

    public final StringPath description = createString("description");

    public final StringPath dimension = createString("dimension");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inquiryCount = createNumber("inquiryCount", Long.class);

    public final EnumPath<com.arto.arto.domain.artwork.type.Mood> mood = createEnum("mood", com.arto.arto.domain.artwork.type.Mood.class);

    public final EnumPath<com.arto.arto.domain.artwork.type.Morph> morph = createEnum("morph", com.arto.arto.domain.artwork.type.Morph.class);

    public final NumberPath<Long> registeredByAdminId = createNumber("registeredByAdminId", Long.class);

    public final NumberPath<java.math.BigDecimal> shippingCost = createNumber("shippingCost", java.math.BigDecimal.class);

    public final EnumPath<com.arto.arto.domain.artwork.type.ShippingMethod> shippingMethod = createEnum("shippingMethod", com.arto.arto.domain.artwork.type.ShippingMethod.class);

    public final StringPath thumbnailImageUrl = createString("thumbnailImageUrl");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

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

