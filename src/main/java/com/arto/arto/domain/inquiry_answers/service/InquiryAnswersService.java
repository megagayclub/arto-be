package com.arto.arto.domain.inquiry_answers.service;

import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.admins.repository.AdminsRepository;
import com.arto.arto.domain.inquiries.entity.InquiriesEntity;
import com.arto.arto.domain.inquiries.repository.InquiriesRepository;
import com.arto.arto.domain.inquiries.type.InquiryStatus;
import com.arto.arto.domain.inquiry_answers.dto.request.InquiryAnswerRequestDto;
import com.arto.arto.domain.inquiry_answers.entity.InquiryAnswersEntity;
import com.arto.arto.domain.inquiry_answers.repository.InquiryAnswersRepository;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InquiryAnswersService {

    private final InquiryAnswersRepository inquiryAnswersRepository;
    private final InquiriesRepository inquiriesRepository;
    private final UsersRepository usersRepository;
    private final AdminsRepository adminsRepository; // 관리자 테이블 접근용

    @Transactional
    public void createAnswer(String email, Long inquiryId, InquiryAnswerRequestDto requestDto) {
        // 1. 현재 로그인한 유저 찾기
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 2. 이 유저가 '관리자(Admin)'인지 확인 (AdminsEntity 조회)
        AdminsEntity admin = adminsRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("管理者権限がありません。")); // 관리자 권한이 없습니다.

        // 3. 문의글 찾기
        InquiriesEntity inquiry = inquiriesRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("お問い合わせが見つかりません。")); // 문의를 찾을 수 없습니다.

        // 4. 답변 엔티티 생성 및 저장
        InquiryAnswersEntity answer = InquiryAnswersEntity.builder()
                .inquiry(inquiry)
                .responder(admin)
                .content(requestDto.getContent())
                .answeredAt(LocalDateTime.now())
                .build();

        inquiryAnswersRepository.save(answer);

        inquiry.setStatus(InquiryStatus.ANSWERED);
    }
}