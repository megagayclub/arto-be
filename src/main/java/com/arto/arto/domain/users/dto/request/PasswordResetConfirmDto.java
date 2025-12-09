package com.arto.arto.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetConfirmDto {

    @NotBlank(message = "トークンは必須です。") // 토큰 필수
    private String token;

    @NotBlank(message = "新しいパスワードは必須です。") // 새 비번 필수
    private String newPassword;
}