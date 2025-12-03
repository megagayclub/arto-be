package com.arto.arto.domain.users.service;

import com.arto.arto.domain.users.dto.request.PasswordChangeRequestDto;
import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.dto.request.UserUpdateRequestDto;
import com.arto.arto.domain.users.dto.response.UserResponseDto;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role; // 👈 파트너님의 Role Enum 위치 확인!
import com.arto.arto.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     */
    @Transactional
    public Long signUp(SignUpRequestDto requestDto) {
        // 1. 이메일 중복 검사
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("既に使用されているメールアドレスです。"); // 이미 사용 중인 이메일입니다.
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 3. Entity 생성 (기본 권한 USER 설정)
        UsersEntity newUser = UsersEntity.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .role(Role.USER) // ✨ 중요: 가입 시 기본은 일반 유저
                .build();

        // 4. DB 저장
        return usersRepository.save(newUser).getUserId();
    }

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public String login(String email, String password) {
        // 1. 이메일로 회원 찾기
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。")); // 사용자를 찾을 수 없습니다.

        // 2. 탈퇴한 회원인지 확인 (선택 사항이지만 안전을 위해 추천)
        // if (!user.isActive()) throw new IllegalArgumentException("脱退した会員です。");

        // 3. 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("パスワードが一致しません。"); // 비밀번호가 일치하지 않습니다.
        }

        // 4. 토큰 발급 (이메일 + 권한 정보 전달)
        // ✨ 중요: Role 정보를 같이 넘겨야 어드민 권한 체크가 가능합니다.
        return jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
    }

    /**
     * 내 정보 조회
     */
    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(String email) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        return UserResponseDto.from(user);
    }

    /**
     * 내 정보 수정
     */
    @Transactional
    public void updateMyInfo(String email, UserUpdateRequestDto requestDto) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        user.setName(requestDto.getName());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setAddress(requestDto.getAddress());
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void changePassword(String email, PasswordChangeRequestDto requestDto) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("現在のパスワードが一致しません。"); // 현재 비밀번호가 일치하지 않습니다.
        }

        // 새 비밀번호 암호화 후 변경
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void withdraw(String email) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        user.setActive(false); // 논리적 삭제 (비활성화)
    }
}