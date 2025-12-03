package com.arto.arto.domain.users.dto.response;

import com.arto.arto.domain.users.entity.UsersEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private String role;

    public static UserResponse fromEntity(UsersEntity entity) {
        return UserResponse.builder()
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .role(entity.getRole().name())
                .build();
    }
}
