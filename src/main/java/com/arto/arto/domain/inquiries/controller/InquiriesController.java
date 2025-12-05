package com.arto.arto.domain.inquiries.controller;

import com.arto.arto.domain.inquiries.dto.request.InquiryCreateRequestDto;
import com.arto.arto.domain.inquiries.service.InquiriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.arto.arto.domain.inquiries.dto.response.InquiryResponseDto;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiriesController {

    private final InquiriesService inquiriesService;

    //문의 등록 API [POST] /api/v1/inquiries
    @PostMapping
    public ResponseEntity<Map<String, Object>> createInquiry(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid InquiryCreateRequestDto requestDto) {

        Long inquiryId = inquiriesService.createInquiry(userDetails.getUsername(), requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "お問い合わせが登録されました。", // 문의가 등록되었습니다.
                "inquiryId", inquiryId
        ));
    }

    //내 문의 내역 조회 API [GET] /api/v1/inquiries
    @GetMapping
    public ResponseEntity<List<InquiryResponseDto>> getMyInquiries(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<InquiryResponseDto> response = inquiriesService.getMyInquiries(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}