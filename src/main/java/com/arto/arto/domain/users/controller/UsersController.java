package com.arto.arto.domain.users.controller;

import com.arto.arto.domain.users.dto.request.UserLoginRequest;
import com.arto.arto.domain.users.dto.request.UserSignupRequest;
import com.arto.arto.domain.users.dto.response.UserResponse;
import com.arto.arto.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody UserSignupRequest request) {
        return usersService.signup(request);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest request) {
        return usersService.login(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return usersService.getUser(id);
    }
}
