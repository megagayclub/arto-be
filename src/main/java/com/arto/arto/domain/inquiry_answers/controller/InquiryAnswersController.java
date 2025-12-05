package com.arto.arto.domain.inquiry_answers.controller;

import com.arto.arto.domain.inquiry_answers.dto.request.InquiryAnswerRequestDto;
import com.arto.arto.domain.inquiry_answers.service.InquiryAnswersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiryAnswersController {

    private final InquiryAnswersService inquiryAnswersService;

    /**
     * 관리자 답변 등록 API
     * [POST] /api/v1/inquiries/{inquiryId}/answer
     */
    //관리자 답변 등록 API [POST] /api/v1/inquiries/{inquiryId}/answer
    @PostMapping("/{inquiryId}/answer")
    public ResponseEntity<Map<String, String>> createAnswer(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("inquiryId") Long inquiryId,
            @RequestBody @Valid InquiryAnswerRequestDto requestDto) {

        // 서비스 호출
        inquiryAnswersService.createAnswer(userDetails.getUsername(), inquiryId, requestDto);

        return ResponseEntity.ok(Map.of("message", "回答が登録されました。"));
    }
}