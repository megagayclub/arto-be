package com.arto.arto.domain.cart_items.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartItemsEntity is a Querydsl query type for CartItemsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartItemsEntity extends EntityPathBase<CartItemsEntity> {

    private static final long serialVersionUID = 1867915048L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartItemsEntity cartItemsEntity = new QCartItemsEntity("cartItemsEntity");

    public final com.arto.arto.domain.artwork.entity.QArtworkEntity artwork;

    public final com.arto.arto.domain.shopping_carts.entity.QShoppingCartsEntity cart;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCartItemsEntity(String variable) {
        this(CartItemsEntity.class, forVariable(variable), INITS);
    }

    public QCartItemsEntity(Path<? extends CartItemsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartItemsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartItemsEntity(PathMetadata metadata, PathInits inits) {
        this(CartItemsEntity.class, metadata, inits);
    }

    public QCartItemsEntity(Class<? extends CartItemsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artwork = inits.isInitialized("artwork") ? new com.arto.arto.domain.artwork.entity.QArtworkEntity(forProperty("artwork")) : null;
        this.cart = inits.isInitialized("cart") ? new com.arto.arto.domain.shopping_carts.entity.QShoppingCartsEntity(forProperty("cart"), inits.get("cart")) : null;
    }

}

