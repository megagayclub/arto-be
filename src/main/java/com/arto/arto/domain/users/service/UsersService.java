package com.arto.arto.domain.users.service;

import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.dto.response.UserResponseDto;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arto.arto.global.util.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
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

    private final JwtTokenProvider jwtTokenProvider; // 1. 아까 만든 토큰 기계 주입
    // (※ 필드에 private final JwtTokenProvider jwtTokenProvider; 추가하고, 생성자 주입 확인하세요!)

    //로그인
    @Transactional(readOnly = true) // 조회만 하니까 readOnly = true (성능 향상)
    public String login(String email, String password) {

        // 1. 이메일로 회원 찾기 (없으면 에러)
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 검증 (입력받은 비번 vs DB의 암호화된 비번 비교)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 성공, 토큰 생성해서 반환
        return jwtTokenProvider.createToken(user.getEmail());
    }

    //내 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(String email) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        return UserResponseDto.from(user);
    }
}