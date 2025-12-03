package com.arto.arto.domain.users.controller;

import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    /**
     * 회원가입 API
     * [POST] /api/v1/users
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {

        // 1. 서비스에게 회원가입 처리를 맡깁니다.
        Long userId = usersService.signUp(requestDto);

        // 2. 성공했다면, 약속했던 201 Created 상태 코드와 함께 응답을 보냅니다.
        // (응답 본문은 간단하게 성공 메시지와 ID를 담아봅니다)
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "회원가입이 완료되었습니다.",
                "userId", userId
        ));
    }
}