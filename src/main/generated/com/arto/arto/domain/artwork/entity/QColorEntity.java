package com.arto.arto.domain.artwork.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QColorEntity is a Querydsl query type for ColorEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColorEntity extends EntityPathBase<ColorEntity> {

    private static final long serialVersionUID = -1433595662L;

    public static final QColorEntity colorEntity = new QColorEntity("colorEntity");

    public final SetPath<ArtworkEntity, QArtworkEntity> artworks = this.<ArtworkEntity, QArtworkEntity>createSet("artworks", ArtworkEntity.class, QArtworkEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QColorEntity(String variable) {
        super(ColorEntity.class, forVariable(variable));
    }

    public QColorEntity(Path<? extends ColorEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QColorEntity(PathMetadata metadata) {
        super(ColorEntity.class, metadata);
    }

}

