package com.arto.arto.domain.inquiries.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiriesEntity is a Querydsl query type for InquiriesEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiriesEntity extends EntityPathBase<InquiriesEntity> {

    private static final long serialVersionUID = 1951762339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiriesEntity inquiriesEntity = new QInquiriesEntity("inquiriesEntity");

    public final com.arto.arto.domain.admins.entity.QAdminsEntity admin;

    public final com.arto.arto.domain.artwork.entity.QArtworkEntity artwork;

    public final EnumPath<com.arto.arto.domain.inquiries.type.InquiryCategory> category = createEnum("category", com.arto.arto.domain.inquiries.type.InquiryCategory.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> inquiryId = createNumber("inquiryId", Long.class);

    public final com.arto.arto.domain.users.entity.QUsersEntity sender;

    public final EnumPath<com.arto.arto.domain.inquiries.type.InquiryStatus> status = createEnum("status", com.arto.arto.domain.inquiries.type.InquiryStatus.class);

    public final StringPath title = createString("title");

    public QInquiriesEntity(String variable) {
        this(InquiriesEntity.class, forVariable(variable), INITS);
    }

    public QInquiriesEntity(Path<? extends InquiriesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiriesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiriesEntity(PathMetadata metadata, PathInits inits) {
        this(InquiriesEntity.class, metadata, inits);
    }

    public QInquiriesEntity(Class<? extends InquiriesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new com.arto.arto.domain.admins.entity.QAdminsEntity(forProperty("admin"), inits.get("admin")) : null;
        this.artwork = inits.isInitialized("artwork") ? new com.arto.arto.domain.artwork.entity.QArtworkEntity(forProperty("artwork")) : null;
        this.sender = inits.isInitialized("sender") ? new com.arto.arto.domain.users.entity.QUsersEntity(forProperty("sender")) : null;
    }

}

