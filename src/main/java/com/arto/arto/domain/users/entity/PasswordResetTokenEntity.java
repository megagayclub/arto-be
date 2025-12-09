package com.arto.arto.domain.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_password_reset_tokens")
public class PasswordResetTokenEntity {

    private static final long EXPIRATION_MINUTES = 60 * 24; // 유효시간 24시간

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = UsersEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UsersEntity user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    // 토큰 만료 여부 확인 메서드
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}