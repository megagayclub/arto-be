package com.arto.arto.domain.user_activities.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserActivitiesEntity is a Querydsl query type for UserActivitiesEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserActivitiesEntity extends EntityPathBase<UserActivitiesEntity> {

    private static final long serialVersionUID = 1158317628L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserActivitiesEntity userActivitiesEntity = new QUserActivitiesEntity("userActivitiesEntity");

    public final NumberPath<Long> activityId = createNumber("activityId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath deviceInfo = createString("deviceInfo");

    public final NumberPath<Long> durationSeconds = createNumber("durationSeconds", Long.class);

    public final EnumPath<com.arto.arto.domain.user_activities.type.EventType> eventType = createEnum("eventType", com.arto.arto.domain.user_activities.type.EventType.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final StringPath pageUrl = createString("pageUrl");

    public final StringPath referrerUrl = createString("referrerUrl");

    public final StringPath sessionId = createString("sessionId");

    public final com.arto.arto.domain.users.entity.QUsersEntity user;

    public QUserActivitiesEntity(String variable) {
        this(UserActivitiesEntity.class, forVariable(variable), INITS);
    }

    public QUserActivitiesEntity(Path<? extends UserActivitiesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserActivitiesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserActivitiesEntity(PathMetadata metadata, PathInits inits) {
        this(UserActivitiesEntity.class, metadata, inits);
    }

    public QUserActivitiesEntity(Class<? extends UserActivitiesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("user")) : null;
    }

}

