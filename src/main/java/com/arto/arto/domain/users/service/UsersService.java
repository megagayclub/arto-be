package com.arto.arto.domain.users.service;

import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 로직
     */
    @Transactional
    public Long signUp(SignUpRequestDto requestDto) {
        // 1. 이메일 중복 검사
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            // TODO: 나중에 우리가 설계한 커스텀 예외(EmailDuplicateException)로 바꿔야 합니다.
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 3. DTO -> Entity 변환 (비밀번호는 암호화된 것으로 넣음)
        UsersEntity newUser = requestDto.toEntity(encodedPassword);

        // 4. DB 저장
        UsersEntity savedUser = usersRepository.save(newUser);

        return savedUser.getUserId();
    }
}