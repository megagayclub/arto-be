package com.arto.arto.domain.payments.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentsEntity is a Querydsl query type for PaymentsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentsEntity extends EntityPathBase<PaymentsEntity> {

    private static final long serialVersionUID = -605979209L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentsEntity paymentsEntity = new QPaymentsEntity("paymentsEntity");

    public final com.arto.arto.domain.orders.entity.QOrdersEntity order;

    public final NumberPath<java.math.BigDecimal> paymentAmount = createNumber("paymentAmount", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> paymentDate = createDateTime("paymentDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final EnumPath<com.arto.arto.domain.payments.type.PaymentMethod> paymentMethod = createEnum("paymentMethod", com.arto.arto.domain.payments.type.PaymentMethod.class);

    public final EnumPath<com.arto.arto.domain.payments.type.PaymentStatus> paymentStatus = createEnum("paymentStatus", com.arto.arto.domain.payments.type.PaymentStatus.class);

    public final StringPath transactionId = createString("transactionId");

    public QPaymentsEntity(String variable) {
        this(PaymentsEntity.class, forVariable(variable), INITS);
    }

    public QPaymentsEntity(Path<? extends PaymentsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentsEntity(PathMetadata metadata, PathInits inits) {
        this(PaymentsEntity.class, metadata, inits);
    }

    public QPaymentsEntity(Class<? extends PaymentsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.arto.arto.domain.orders.entity.QOrdersEntity(forProperty("order"), inits.get("order")) : null;
    }

}

