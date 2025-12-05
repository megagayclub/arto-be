package com.arto.arto.domain.shopping_carts.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingCartsEntity is a Querydsl query type for ShoppingCartsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoppingCartsEntity extends EntityPathBase<ShoppingCartsEntity> {

    private static final long serialVersionUID = -1899111304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoppingCartsEntity shoppingCartsEntity = new QShoppingCartsEntity("shoppingCartsEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.arto.arto.domain.users.entity.QUsersEntity user;

    public QShoppingCartsEntity(String variable) {
        this(ShoppingCartsEntity.class, forVariable(variable), INITS);
    }

    public QShoppingCartsEntity(Path<? extends ShoppingCartsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoppingCartsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoppingCartsEntity(PathMetadata metadata, PathInits inits) {
        this(ShoppingCartsEntity.class, metadata, inits);
    }

    public QShoppingCartsEntity(Class<? extends ShoppingCartsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("user")) : null;
    }

}

