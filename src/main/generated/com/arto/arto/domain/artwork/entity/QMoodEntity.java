package com.arto.arto.domain.artwork.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMoodEntity is a Querydsl query type for MoodEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoodEntity extends EntityPathBase<MoodEntity> {

    private static final long serialVersionUID = 1071260782L;

    public static final QMoodEntity moodEntity = new QMoodEntity("moodEntity");

    public final SetPath<ArtworkEntity, QArtworkEntity> artworks = this.<ArtworkEntity, QArtworkEntity>createSet("artworks", ArtworkEntity.class, QArtworkEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QMoodEntity(String variable) {
        super(MoodEntity.class, forVariable(variable));
    }

    public QMoodEntity(Path<? extends MoodEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMoodEntity(PathMetadata metadata) {
        super(MoodEntity.class, metadata);
    }

}

