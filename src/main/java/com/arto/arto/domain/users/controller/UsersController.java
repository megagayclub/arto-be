package com.arto.arto.domain.users.controller;

import com.arto.arto.domain.users.dto.request.LoginRequestDto;
import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.dto.response.UserResponseDto;
import com.arto.arto.domain.users.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    //회원가입 API[POST] /api/v1/users
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        Long userId = usersService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "회원가입이 완료되었습니다.",
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
}