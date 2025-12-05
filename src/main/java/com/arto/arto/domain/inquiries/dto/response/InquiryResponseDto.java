package com.arto.arto.domain.inquiries.dto.response;

import com.arto.arto.domain.inquiries.entity.InquiriesEntity;
import com.arto.arto.domain.inquiries.type.InquiryCategory;
import com.arto.arto.domain.inquiries.type.InquiryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InquiryResponseDto {

    private Long inquiryId;
    private String title;
    private String content;
    private InquiryCategory category;
    private InquiryStatus status;
    private LocalDateTime createdAt;
    private String artworkTitle;

    // 👇 [추가] 답변 내용과 답변 시간
    private String answerContent;
    private LocalDateTime answeredAt;

    public static InquiryResponseDto fromEntity(InquiriesEntity entity) {
        // 답변이 있는지 확인해서 가져오기 (리스트의 첫 번째 답변)
        String answerContent = null;
        LocalDateTime answeredAt = null;

        if (entity.getAnswers() != null && !entity.getAnswers().isEmpty()) {
            // 답변 리스트에서 가장 최근 것(혹은 첫 번째) 하나만 가져옴
            var answerEntity = entity.getAnswers().get(0);
            answerContent = answerEntity.getContent();
            answeredAt = answerEntity.getAnsweredAt();
        }

        return InquiryResponseDto.builder()
                .inquiryId(entity.getInquiryId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .artworkTitle(entity.getArtwork() != null ? entity.getArtwork().getTitle() : null)
                // 👇 [추가] DTO에 담기
                .answerContent(answerContent)
                .answeredAt(answeredAt)
                .build();
    }
}