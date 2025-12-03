package com.arto.arto.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "メールアドレスを入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください。")
    private String password;
}