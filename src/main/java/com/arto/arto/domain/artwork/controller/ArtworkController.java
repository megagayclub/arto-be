package com.arto.arto.domain.artwork.controller;

import com.arto.arto.domain.artwork.dto.request.ArtworkCreateRequestDto;
import com.arto.arto.domain.artwork.dto.response.ArtworkDetailResponseDto;
import com.arto.arto.domain.artwork.service.ArtworkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.arto.arto.domain.artwork.dto.request.ArtworkSearchCondition;
import com.arto.arto.domain.artwork.dto.response.ArtworkSimpleResponseDto;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;


    //작품 등록 API [POST] /api/v1/artworks
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Object>> createArtwork(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("dto") @Valid ArtworkCreateRequestDto requestDto,
            @RequestPart("file") MultipartFile file) { // 단일 파일 업로드 추가

        // Service에 파일 전달 (Service 로직도 수정이 필요합니다)
        Long artworkId = artworkService.createArtwork(userDetails.getUsername(), requestDto, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "作品が正常に登録されました。",//작품이 정상적으로 등록되었습니다.
                "artworkId", artworkId
        ));
    }

    //작품 상세 페이지 API [GET] /api/v1/artworks/{artworkId}
    @GetMapping("/{artworkId}")
    public ResponseEntity<ArtworkDetailResponseDto> getArtworkDetail(@PathVariable("artworkId") Long artworkId) {

        ArtworkDetailResponseDto response = artworkService.getArtworkDetail(artworkId);

        return ResponseEntity.ok(response);
    }

    //작품 리스트 불러오는 api
    @GetMapping
    public ResponseEntity<List<ArtworkSimpleResponseDto>> getArtworkList(
            @ModelAttribute ArtworkSearchCondition condition) {

        List<ArtworkSimpleResponseDto> response = artworkService.getArtworkList(condition);

        return ResponseEntity.ok(response);
    }

    // 작품 삭제 API [DELETE] /api/v1/artworks/{artworkId}
    @DeleteMapping("/{artworkId}")
    public ResponseEntity<Map<String, String>> deleteArtwork(@PathVariable("artworkId") Long artworkId) {
        artworkService.deleteArtwork(artworkId);

        return ResponseEntity.ok(Map.of(
                "message", "작품과 이미지가 성공적으로 삭제되었습니다."
        ));
    }

    // 검색 필터 목록 조회 API [GET] /api/v1/artworks/filters
    @GetMapping("/filters")
    public ResponseEntity<Map<String, Object>> getSearchFilters() {
        // Service에서 모든 필터 데이터를 가져옵니다.
        // (만약 Service에 로직이 없다면 아래 구조대로 Service에 추가하면 됩니다!)
        Map<String, Object> filters = artworkService.getAllFilters();

        return ResponseEntity.ok(filters);
    }
}