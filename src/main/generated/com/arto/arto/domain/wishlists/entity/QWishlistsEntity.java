package com.arto.arto.domain.wishlists.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishlistsEntity is a Querydsl query type for WishlistsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishlistsEntity extends EntityPathBase<WishlistsEntity> {

    private static final long serialVersionUID = 155563715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishlistsEntity wishlistsEntity = new QWishlistsEntity("wishlistsEntity");

    public final DateTimePath<java.time.LocalDateTime> addedAt = createDateTime("addedAt", java.time.LocalDateTime.class);

    public final com.arto.arto.domain.artwork.entity.QArtworkEntity artwork;

    public final com.arto.arto.domain.users.entity.QUsersEntity user;

    public final NumberPath<Long> wishlistId = createNumber("wishlistId", Long.class);

    public QWishlistsEntity(String variable) {
        this(WishlistsEntity.class, forVariable(variable), INITS);
    }

    public QWishlistsEntity(Path<? extends WishlistsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishlistsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishlistsEntity(PathMetadata metadata, PathInits inits) {
        this(WishlistsEntity.class, metadata, inits);
    }

    public QWishlistsEntity(Class<? extends WishlistsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artwork = inits.isInitialized("artwork") ? new com.arto.arto.domain.artwork.entity.QArtworkEntity(forProperty("artwork"), inits.get("artwork")) : null;
        this.user = inits.isInitialized("user") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("user")) : null;
    }

}

