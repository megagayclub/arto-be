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

    @NotBlank(message = "メールアドレスは必須項目です。")
    @Email(message = "メールアドレスの形式が正しくありません。")
    private String email;

    @NotBlank(message = "パスワードは必須項目です。")
    private String password;

    @NotBlank(message = "名前は必須項目です。")
    private String name;


    private String phoneNumber; // 선택

    @NotBlank(message = "住所は必須です。")
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