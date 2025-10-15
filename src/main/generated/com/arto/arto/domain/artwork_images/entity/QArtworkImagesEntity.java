package com.arto.arto.domain.artwork_images.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtworkImagesEntity is a Querydsl query type for ArtworkImagesEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtworkImagesEntity extends EntityPathBase<ArtworkImagesEntity> {

    private static final long serialVersionUID = 708556978L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtworkImagesEntity artworkImagesEntity = new QArtworkImagesEntity("artworkImagesEntity");

    public final com.arto.arto.domain.artwork.entity.QArtworkEntity artwork;

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final EnumPath<com.arto.arto.domain.artwork_images.type.ImageType> imageType = createEnum("imageType", com.arto.arto.domain.artwork_images.type.ImageType.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Integer> orderIndex = createNumber("orderIndex", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> uploadedAt = createDateTime("uploadedAt", java.time.LocalDateTime.class);

    public QArtworkImagesEntity(String variable) {
        this(ArtworkImagesEntity.class, forVariable(variable), INITS);
    }

    public QArtworkImagesEntity(Path<? extends ArtworkImagesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtworkImagesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtworkImagesEntity(PathMetadata metadata, PathInits inits) {
        this(ArtworkImagesEntity.class, metadata, inits);
    }

    public QArtworkImagesEntity(Class<? extends ArtworkImagesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artwork = inits.isInitialized("artwork") ? new com.arto.arto.domain.artwork.entity.QArtworkEntity(forProperty("artwork")) : null;
    }

}

