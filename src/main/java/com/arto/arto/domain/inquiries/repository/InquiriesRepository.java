package com.arto.arto.domain.inquiries.repository;

import com.arto.arto.domain.inquiries.entity.InquiriesEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InquiriesRepository extends JpaRepository<InquiriesEntity, Long> {
    // 내 문의 목록 보기 (최신순)
    List<InquiriesEntity> findAllBySenderOrderByCreatedAtDesc(UsersEntity sender);
}