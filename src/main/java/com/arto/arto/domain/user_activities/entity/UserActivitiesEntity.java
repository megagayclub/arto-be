package com.arto.arto.domain.user_activities.entity;
import com.arto.arto.domain.user_activities.type.EventType;
import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_user_activities")
public class UserActivitiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 비로그인 시 NULL 허용 (nullable = true가 기본값)
    private UsersEntity user;

    @Column(name = "session_id", nullable = false, length = 255)
    private String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "page_url", length = 1024)
    private String pageUrl;

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @Column(name = "referrer_url", length = 1024)
    private String referrerUrl;

    @Column(name = "device_info", length = 255)
    private String deviceInfo;

    @Column(name = "duration_seconds")
    private Long durationSeconds;

    @CreationTimestamp // 엔티티 생성 시 자동으로 현재 시간 저장
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
