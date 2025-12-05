package com.arto.arto.domain.admins.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminsEntity is a Querydsl query type for AdminsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminsEntity extends EntityPathBase<AdminsEntity> {

    private static final long serialVersionUID = 1842093989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminsEntity adminsEntity = new QAdminsEntity("adminsEntity");

    public final NumberPath<Long> adminId = createNumber("adminId", Long.class);

    public final NumberPath<Integer> adminLevel = createNumber("adminLevel", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> lastActionAt = createDateTime("lastActionAt", java.time.LocalDateTime.class);

    public final com.arto.arto.domain.users.entity.QUsersEntity user;

    public final DateTimePath<java.time.LocalDateTime> validUntil = createDateTime("validUntil", java.time.LocalDateTime.class);

    public QAdminsEntity(String variable) {
        this(AdminsEntity.class, forVariable(variable), INITS);
    }

    public QAdminsEntity(Path<? extends AdminsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminsEntity(PathMetadata metadata, PathInits inits) {
        this(AdminsEntity.class, metadata, inits);
    }

    public QAdminsEntity(Class<? extends AdminsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("user")) : null;
    }

}

