package com.arto.arto.domain.inquiry_answers.entity;

import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.inquiries.entity.InquiriesEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_inquiry_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryAnswersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private InquiriesEntity inquiry;  // inquiries 테이블 연결

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responder_id", nullable = false)
    private AdminsEntity responder;  // admins 테이블 연결

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "answered_at", nullable = false)
    private LocalDateTime answeredAt;
}
