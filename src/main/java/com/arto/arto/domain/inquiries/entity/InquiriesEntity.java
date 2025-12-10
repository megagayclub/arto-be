package com.arto.arto.domain.inquiries.entity;
import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.inquiries.type.InquiryCategory;
import com.arto.arto.domain.inquiries.type.InquiryStatus;
import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import java.time.LocalDateTime;
import com.arto.arto.domain.inquiry_answers.entity.InquiryAnswersEntity;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "tbl_inquiries")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InquiriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long inquiryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UsersEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminsEntity admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtworkEntity artwork;

    @Column(length = 255, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryCategory category;

    //질문에 달린 답변 가져오기
    @Builder.Default
    @OneToMany(mappedBy = "inquiry", fetch = FetchType.LAZY)
    private List<InquiryAnswersEntity> answers = new ArrayList<>();
}
