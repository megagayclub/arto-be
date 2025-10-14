package com.arto.arto.global.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter

public class BaseTimeEntity {
    @CreatedDate //등록일
    @Column(updatable = false)
    protected LocalDateTime createdDateTime;

    @LastModifiedDate //마지막 수정일
    protected LocalDateTime modifiedDateTime;



}
