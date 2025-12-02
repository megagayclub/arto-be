package com.arto.arto.domain.users.dto.request;

import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.type.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private String phoneNumber; // 선택

    private String address;     // 선택

    // DTO를 Entity로 변환하는 메서드 (Service에서 사용)
    public UsersEntity toEntity(String encodedPassword) {
        UsersEntity user = new UsersEntity();
        user.setEmail(this.email);
        user.setPassword(encodedPassword); // 암호화된 비밀번호 저장
        user.setName(this.name);
        user.setPhoneNumber(this.phoneNumber);
        user.setAddress(this.address);
        user.setRole(Role.USER); // 기본 권한
        user.setActive(true);    // 계정 활성화
        return user;
    }
}