package com.arto.arto.domain.inquiries.dto.request;

import com.arto.arto.domain.inquiries.type.InquiryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InquiryCreateRequestDto {

    @NotBlank(message = "タイトルは必須です。")
    private String title;

    @NotBlank(message = "内容は必須です。")
    private String content;

    @NotNull(message = "カテゴリは必須です。")
    private InquiryCategory category;

    // 어떤 작품에 대한 문의인지 (null 가능)
    private Long artworkId;
}