package com.arto.arto.domain.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetRequestDto {
    @NotBlank(message = "メールアドレスは必須です。") // 이메일 필수
    @Email(message = "有効なメールアドレス形式ではありません。") // 이메일 형식
    private String email;
}