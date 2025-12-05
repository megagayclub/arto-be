package com.arto.arto.domain.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemAddRequest {

    @NotNull(message = "작품 ID는 필수입니다.")
    private Long artworkId;
}
