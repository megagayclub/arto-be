package com.arto.arto.domain.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersEntity is a Querydsl query type for UsersEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersEntity extends EntityPathBase<UsersEntity> {

    private static final long serialVersionUID = 828249219L;

    public static final QUsersEntity usersEntity = new QUsersEntity("usersEntity");

    public final StringPath address = createString("address");

    public final StringPath email = createString("email");

    public final BooleanPath isActive = createBoolean("isActive");

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final EnumPath<com.arto.arto.domain.users.type.Role> role = createEnum("role", com.arto.arto.domain.users.type.Role.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUsersEntity(String variable) {
        super(UsersEntity.class, forVariable(variable));
    }

    public QUsersEntity(Path<? extends UsersEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersEntity(PathMetadata metadata) {
        super(UsersEntity.class, metadata);
    }

}

