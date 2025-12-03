package com.arto.arto.domain.users.service;

import com.arto.arto.domain.users.dto.request.SignUpRequestDto;
import com.arto.arto.domain.users.dto.response.UserResponseDto;
import com.arto.arto.domain.users.dto.request.PasswordChangeRequestDto;
import com.arto.arto.domain.users.dto.request.UserUpdateRequestDto;
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
            throw new IllegalArgumentException("既に使用されているメールアドレスです。");
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
                .orElseThrow(() -> new IllegalArgumentException("登録されていないメールアドレスです。"));

        // 2. 비밀번호 검증 (입력받은 비번 vs DB의 암호화된 비번 비교)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("パスワードが一致しません。");
        }

        // 3. 인증 성공, 토큰 생성해서 반환
        return jwtTokenProvider.createToken(user.getEmail());
    }

    //내 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(String email) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザー情報が見つかりません。"));

        return UserResponseDto.from(user);
    }

    //내 정보 수정
    @Transactional
    public void updateMyInfo(String email, UserUpdateRequestDto requestDto) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザー情報が見つかりません。"));

        // Entity의 Setter를 사용해 정보 수정 (Dirty Checking으로 자동 저장됨)
        user.setName(requestDto.getName());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setAddress(requestDto.getAddress());
    }

    //비밀번호 변경
    @Transactional
    public void changePassword(String email, PasswordChangeRequestDto requestDto) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザー情報が見つかりません。"));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("現在のパスワードが一致しません。");
        }

        // 새 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }

    //회원 탈퇴(논리적 삭제)
    @Transactional
    public void withdraw(String email) {
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 계정 비활성화 (is_active = false)
        user.setActive(false);
    }
}