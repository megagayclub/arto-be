package com.arto.arto.domain.admins.entity;

import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_admins")
public class AdminsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Column(name = "admin_level", nullable = false)
    private Integer adminLevel;

    @Column(name = "last_action_at")
    private LocalDateTime lastActionAt;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;
}
