package com.arto.arto.domain.users.service;

import com.arto.arto.domain.users.dto.request.PasswordChangeRequestDto;
import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.dto.request.UserUpdateRequestDto;
import com.arto.arto.domain.users.dto.response.UserResponseDto;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import com.arto.arto.domain.users.type.Role;
import com.arto.arto.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arto.arto.domain.users.entity.PasswordResetTokenEntity;
import com.arto.arto.domain.users.repository.PasswordResetTokenRepository;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

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
                .role(Role.USER)
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
     * 마이페이지 비밀번호 변경
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
     * 로그인 과정에서 메일로 비밀번호 변경
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // 1. 토큰으로 DB 조회
        PasswordResetTokenEntity resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("有効ではないトークンです。")); // 유효하지 않은 토큰

        // 2. 만료 시간 확인
        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("期限切れのトークンです。"); // 만료된 토큰
        }

        // 3. 비밀번호 변경 (암호화 필수!)
        UsersEntity user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);

        // 4. 사용한 토큰 삭제 (재사용 방지)
        tokenRepository.delete(resetToken);
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

    @Transactional
    public void sendResetLink(String email) {
        // 1. 유저 확인
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 2. 기존에 발급된 토큰이 있다면 삭제 (새로 발급하기 위해)
        tokenRepository.findByUser(user).ifPresent(token -> {
            tokenRepository.delete(token);
            tokenRepository.flush(); // 즉시 삭제 반영
        });

        // 3. 랜덤 토큰 생성 (예: a1b2-c3d4-...)
        String token = UUID.randomUUID().toString();

        // 4. DB에 토큰 저장
        PasswordResetTokenEntity resetToken = PasswordResetTokenEntity.builder()
                .token(token)
                .user(user)
                .expiryDate(java.time.LocalDateTime.now().plusHours(24)) // 24시간 유효
                .build();

        tokenRepository.save(resetToken);

        // 5. 이메일 발송
        // (실제 서비스에선 프론트엔드 주소로 보내야 함. 여기선 테스트용 로컬 주소)
        String resetLink = "http://localhost:8080/api/v1/users/reset-password?token=" + token;

        String subject = "[Arto] パスワード再設定リンク"; // [Arto] 비밀번호 재설정 링크
        String text = "<p>以下のリンクをクリックしてパスワードを再設定してください。</p>" + // 아래 링크를 클릭해서 비번 재설정하세요
                "<a href='" + resetLink + "'>パスワード再設定</a><br><br>" +
                "(リンクは24時間有効です)"; // 링크는 24시간 유효

        emailService.sendEmail(user.getEmail(), subject, text);
    }
}