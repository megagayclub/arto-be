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

    public final StringPath description = createString("description");

    public final StringPath dimensions = createString("dimensions");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.arto.arto.domain.artwork_images.entity.ArtworkImagesEntity, com.arto.arto.domain.artwork_images.entity.QArtworkImagesEntity> images = this.<com.arto.arto.domain.artwork_images.entity.ArtworkImagesEntity, com.arto.arto.domain.artwork_images.entity.QArtworkImagesEntity>createList("images", com.arto.arto.domain.artwork_images.entity.ArtworkImagesEntity.class, com.arto.arto.domain.artwork_images.entity.QArtworkImagesEntity.class, PathInits.DIRECT2);


}

