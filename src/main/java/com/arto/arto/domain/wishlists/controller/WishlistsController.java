package com.arto.arto.domain.wishlists.controller;

import com.arto.arto.domain.wishlists.service.WishlistsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.arto.arto.domain.wishlists.dto.response.WishlistResponseDto;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishlistsController {

    private final WishlistsService wishlistsService;


    //찜하기
    @PostMapping("/{artworkId}")
    public ResponseEntity<Map<String, Object>> toggleWishlist(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("artworkId") Long artworkId) {

        boolean isAdded = wishlistsService.toggleWishlist(userDetails.getUsername(), artworkId);

        String message = isAdded ? "ウィッシュリストに追加されました。" : "ウィッシュリストから削除されました。";

        return ResponseEntity.ok(Map.of(
                "message", message,
                "isAdded", isAdded
        ));
    }

    @DeleteMapping("/{artworkId}")
    public ResponseEntity<String> deleteWishlist(
            Principal principal,
            @PathVariable("artworkId") Long artworkId // ("artworkId")를 추가하세요!
    ) {
        wishlistsService.deleteWishlist(principal.getName(), artworkId);
        return ResponseEntity.ok("찜이 취소되었습니다.");
    }


    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> getMyWishlists(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<WishlistResponseDto> response = wishlistsService.getMyWishlists(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}