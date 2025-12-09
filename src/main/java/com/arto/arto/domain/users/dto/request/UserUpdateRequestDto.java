package com.arto.arto.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @NotBlank(message = "名前は必須項目です。")
    private String name;

    private String phoneNumber;

    private String address;
}