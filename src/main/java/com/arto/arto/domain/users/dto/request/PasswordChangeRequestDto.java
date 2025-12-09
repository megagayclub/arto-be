package com.arto.arto.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeRequestDto {
    @NotBlank(message = "現在のパスワードを入力してください。")
    private String currentPassword;

    @NotBlank(message = "新しいパスワードを入力してください。")
    private String newPassword;
}