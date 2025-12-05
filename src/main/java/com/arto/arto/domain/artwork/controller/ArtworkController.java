package com.arto.arto.domain.artwork.controller;

import com.arto.arto.domain.artwork.dto.request.ArtworkCreateRequestDto;
import com.arto.arto.domain.artwork.dto.response.ArtworkDetailResponseDto;
import com.arto.arto.domain.artwork.service.ArtworkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.dto.response.ArtworkSimpleResponseDto;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;


    //작품 등록 API [POST] /api/v1/artworks
    @PostMapping
    public ResponseEntity<Map<String, Object>> createArtwork(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid ArtworkCreateRequestDto requestDto) {

        Long artworkId = artworkService.createArtwork(userDetails.getUsername(), requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "作品が正常に登録されました。", //작품이 정상적으로 등록되었습니다.
                "artworkId", artworkId
        ));
    }

    //작품 상세 조회 API [GET] /api/v1/artworks/{artworkId}
    @GetMapping("/{artworkId}")
    public ResponseEntity<ArtworkDetailResponseDto> getArtworkDetail(@PathVariable("artworkId") Long artworkId) {

        ArtworkDetailResponseDto response = artworkService.getArtworkDetail(artworkId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ArtworkSimpleResponseDto>> getArtworkList(
            @ModelAttribute ArtworkSearchCondition condition) {

        List<ArtworkSimpleResponseDto> response = artworkService.getArtworkList(condition);

        return ResponseEntity.ok(response);
    }
}