package com.arto.arto.domain.users.controller;

import com.arto.arto.domain.users.dto.request.LoginRequestDto;
import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.dto.response.UserResponseDto;
import com.arto.arto.domain.users.dto.request.PasswordChangeRequestDto;
import com.arto.arto.domain.users.dto.request.UserUpdateRequestDto;
import com.arto.arto.domain.users.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    //회원가입 API[POST] /api/v1/users
    @PostMapping("/users/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        Long userId = usersService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "会員登録が完了しました。",
                "userId", userId
        ));
    }

    //로그인 API[POST] /api/v1/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestDto requestDto) {
        String token = usersService.login(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "tokenType", "Bearer"
        ));
    }

    //내 정보 조회 API[GET] /api/v1/me
    @GetMapping("/me") // /api/v1 + /me
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {

        // userDetails.getUsername()에는 토큰에서 꺼낸 '이메일'이 들어있습니다.
        UserResponseDto myInfo = usersService.getMyInfo(userDetails.getUsername());

        return ResponseEntity.ok(myInfo);
    }

    //내 정보 수정[PUT] API /api/v1/me
    @PutMapping("/me")
    public ResponseEntity<Map<String, String>> updateMyInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid UserUpdateRequestDto requestDto) {

        usersService.updateMyInfo(userDetails.getUsername(), requestDto);

        return ResponseEntity.ok(Map.of("message", "情報が更新されました。"));
    }

    //비밀번호 변경 API[PUT] /api/v1/me/password
    @PutMapping("/me/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid PasswordChangeRequestDto requestDto) {

        usersService.changePassword(userDetails.getUsername(), requestDto);

        return ResponseEntity.ok(Map.of("message", "パスワードが変更されました。"));
    }

    //회원 탈퇴 API[DELETE] /api/v1/me
    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal UserDetails userDetails) {

        usersService.withdraw(userDetails.getUsername());

        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }
}