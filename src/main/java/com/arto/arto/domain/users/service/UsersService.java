package com.arto.arto.domain.users.service;

import com.arto.arto.domain.users.dto.request.UserLoginRequest;
import com.arto.arto.domain.users.dto.request.UserSignupRequest;
import com.arto.arto.domain.users.dto.response.UserResponse;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role;
import com.arto.arto.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;

    // 회원가입
    @Transactional
    public UserResponse signup(UserSignupRequest request) {

        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(
                    HttpStatus.CONFLICT.value(),
                    "이미 존재하는 이메일입니다."
            );
        }

        UsersEntity user = new UsersEntity();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // ⚠️ 암호화는 나중에!
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setRole(Role.USER);

        usersRepository.save(user);

        return UserResponse.fromEntity(user);
    }


    // 로그인
    public UserResponse login(UserLoginRequest request) {

        UsersEntity user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "해당 이메일을 가진 사용자를 찾을 수 없습니다."
                ));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new CustomException(
                    HttpStatus.UNAUTHORIZED.value(),
                    "비밀번호가 일치하지 않습니다."
            );
        }

        return UserResponse.fromEntity(user);
    }

    // 단건 조회
    public UserResponse getUser(Long id) {
        UsersEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "해당 사용자를 찾을 수 없습니다."
                ));

        return UserResponse.fromEntity(user);
    }
}
