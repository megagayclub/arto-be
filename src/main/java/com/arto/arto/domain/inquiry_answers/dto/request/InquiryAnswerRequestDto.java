package com.arto.arto.domain.inquiry_answers.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InquiryAnswerRequestDto {

    @NotBlank(message = "回答内容は必須です。") // 답변 내용은 필수입니다.
    private String content;
}