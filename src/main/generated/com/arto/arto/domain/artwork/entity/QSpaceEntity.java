package com.arto.arto.domain.artwork.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpaceEntity is a Querydsl query type for SpaceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpaceEntity extends EntityPathBase<SpaceEntity> {

    private static final long serialVersionUID = -930731L;

    public static final QSpaceEntity spaceEntity = new QSpaceEntity("spaceEntity");

    public final SetPath<ArtworkEntity, QArtworkEntity> artworks = this.<ArtworkEntity, QArtworkEntity>createSet("artworks", ArtworkEntity.class, QArtworkEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QSpaceEntity(String variable) {
        super(SpaceEntity.class, forVariable(variable));
    }

    public QSpaceEntity(Path<? extends SpaceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpaceEntity(PathMetadata metadata) {
        super(SpaceEntity.class, metadata);
    }

}

