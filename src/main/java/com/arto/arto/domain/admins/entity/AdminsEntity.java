package com.arto.arto.domain.admins.entity;

import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_admins")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AdminsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Column(name = "admin_level", nullable = false)
    private Integer adminLevel;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "last_action_at")
    private LocalDateTime lastActionAt;
}