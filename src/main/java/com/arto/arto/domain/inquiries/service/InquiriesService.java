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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arto.arto.domain.inquiries.dto.response.InquiryResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InquiriesService {

    private final InquiriesRepository inquiriesRepository;
    private final UsersRepository usersRepository;
    private final ArtworkRepository artworkRepository;

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

        return inquiriesRepository.save(inquiry).getInquiryId();
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