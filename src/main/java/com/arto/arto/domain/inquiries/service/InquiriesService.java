package com.arto.arto.domain.inquiries.service;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.artwork.repository.ArtworkRepository;
import com.arto.arto.domain.inquiries.dto.request.InquiryCreateRequestDto;
import com.arto.arto.domain.inquiries.entity.InquiriesEntity;
import com.arto.arto.domain.inquiries.repository.InquiriesRepository;
import com.arto.arto.domain.inquiries.type.InquiryStatus;
import com.arto.arto.domain.users.entity.UsersEntity;
import com.arto.arto.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arto.arto.domain.inquiries.dto.response.InquiryResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class InquiriesService {

    private final InquiriesRepository inquiriesRepository;
    private final UsersRepository usersRepository;
    private final ArtworkRepository artworkRepository;
    private final JavaMailSender mailSender;

    // 문의 등록
    @Transactional
    public Long createInquiry(String email, InquiryCreateRequestDto requestDto) {
        // 1. 작성자 확인
        UsersEntity sender = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 2. 작품 확인 (artworkId가 있을 경우에만)
        ArtworkEntity artwork = null;
        if (requestDto.getArtworkId() != null) {
            artwork = artworkRepository.findById(requestDto.getArtworkId())
                    .orElseThrow(() -> new IllegalArgumentException("作品が見つかりません。"));
        }

        // 3. 엔티티 생성
        InquiriesEntity inquiry = InquiriesEntity.builder()
                .sender(sender)
                .artwork(artwork)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .status(InquiryStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        Long savedId = inquiriesRepository.save(inquiry).getInquiryId();

        sendAdminNotification(inquiry);


        return savedId;
    }


    // 메일 발송 전용 메서드
    private void sendAdminNotification(InquiriesEntity inquiry) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("seunghwanj56@gmail.com"); // 관리자 수신 이메일
        message.setSubject("[ARTO 신규 문의] " + inquiry.getTitle());
        message.setText("새로운 고객 문의가 접수되었습니다.\n\n" +
                "카테고리: " + inquiry.getCategory() + "\n" +
                "작성자: " + inquiry.getSender().getName() + "(" + inquiry.getSender().getEmail() + ")\n" +
                "내용: " + inquiry.getContent() + "\n" +
                "문의 일시: " + inquiry.getCreatedAt());

        mailSender.send(message);
    }

    @Transactional(readOnly = true)
    public List<InquiryResponseDto> getMyInquiries(String email) {
        // 1. 유저 확인
        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません。"));

        // 2. DB에서 내 문의글 다 가져오기
        List<InquiriesEntity> inquiries = inquiriesRepository.findAllBySenderOrderByCreatedAtDesc(user);

        // 3. 엔티티 리스트 -> DTO 리스트로 변환해서 반환
        return inquiries.stream()
                .map(InquiryResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}