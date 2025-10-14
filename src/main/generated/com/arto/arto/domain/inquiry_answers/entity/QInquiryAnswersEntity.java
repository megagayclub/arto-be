package com.arto.arto.domain.inquiry_answers.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryAnswersEntity is a Querydsl query type for InquiryAnswersEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryAnswersEntity extends EntityPathBase<InquiryAnswersEntity> {

    private static final long serialVersionUID = -469693586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryAnswersEntity inquiryAnswersEntity = new QInquiryAnswersEntity("inquiryAnswersEntity");

    public final DateTimePath<java.time.LocalDateTime> answeredAt = createDateTime("answeredAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> answerId = createNumber("answerId", Long.class);

    public final StringPath content = createString("content");

    public final com.arto.arto.domain.inquiries.entity.QInquiriesEntity inquiry;

    public final com.arto.arto.domain.admins.entity.QAdminsEntity responder;

    public QInquiryAnswersEntity(String variable) {
        this(InquiryAnswersEntity.class, forVariable(variable), INITS);
    }

    public QInquiryAnswersEntity(Path<? extends InquiryAnswersEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryAnswersEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryAnswersEntity(PathMetadata metadata, PathInits inits) {
        this(InquiryAnswersEntity.class, metadata, inits);
    }

    public QInquiryAnswersEntity(Class<? extends InquiryAnswersEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiry = inits.isInitialized("inquiry") ? new com.arto.arto.domain.inquiries.entity.QInquiriesEntity(forProperty("inquiry"), inits.get("inquiry")) : null;
        this.responder = inits.isInitialized("responder") ? new com.arto.arto.domain.admins.entity.QAdminsEntity(forProperty("responder"), inits.get("responder")) : null;
    }

}

