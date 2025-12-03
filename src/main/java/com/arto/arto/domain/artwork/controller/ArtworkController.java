package com.arto.arto.domain.artwork.controller;

import com.arto.arto.domain.artwork.dto.response.ArtworkDetailResponse;
import com.arto.arto.domain.artwork.dto.response.ArtworkSimpleResponse;
import com.arto.arto.domain.artwork.service.ArtworkService;
import com.arto.arto.global.common.dto.request.PageRequestDto;
import com.arto.arto.global.common.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    @GetMapping
    public PageResponse<ArtworkSimpleResponse> getArtworks(PageRequestDto pageRequestDto) {
        // /api/artworks?page=0&size=20 이런 식으로 요청 받는다고 가정
        return artworkService.getArtworks(pageRequestDto);
    }

    @GetMapping("/{id}")
    public ArtworkDetailResponse getArtwork(@PathVariable Long id) {
        return artworkService.getArtwork(id);
    }
}
