package com.arto.arto.domain.users.dto.response;

import com.arto.arto.domain.users.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;

    public static UserResponseDto from(UsersEntity user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
}