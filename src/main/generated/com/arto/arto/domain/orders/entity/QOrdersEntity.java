package com.arto.arto.domain.orders.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrdersEntity is a Querydsl query type for OrdersEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersEntity extends EntityPathBase<OrdersEntity> {

    private static final long serialVersionUID = 531378215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrdersEntity ordersEntity = new QOrdersEntity("ordersEntity");

    public final com.arto.arto.domain.artwork.entity.QArtworkEntity artwork;

    public final com.arto.arto.domain.users.entity.QUsersEntity buyer;

    public final DatePath<java.time.LocalDate> deliver = createDate("deliver", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> deliveryStartDate = createDate("deliveryStartDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> orderDate = createDate("orderDate", java.time.LocalDate.class);

    public final StringPath orderStatus = createString("orderStatus");

    public final NumberPath<Integer> postCode = createNumber("postCode", Integer.class);

    public final StringPath receiverName = createString("receiverName");

    public final StringPath shippingAddress = createString("shippingAddress");

    public final StringPath shippingPhoneNumber = createString("shippingPhoneNumber");

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public QOrdersEntity(String variable) {
        this(OrdersEntity.class, forVariable(variable), INITS);
    }

    public QOrdersEntity(Path<? extends OrdersEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrdersEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrdersEntity(PathMetadata metadata, PathInits inits) {
        this(OrdersEntity.class, metadata, inits);
    }

    public QOrdersEntity(Class<? extends OrdersEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artwork = inits.isInitialized("artwork") ? new com.arto.arto.domain.artwork.entity.QArtworkEntity(forProperty("artwork"), inits.get("artwork")) : null;
        this.buyer = inits.isInitialized("buyer") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("buyer")) : null;
    }

}

