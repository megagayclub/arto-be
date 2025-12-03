package com.arto.arto.domain.artwork.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ArtworkCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String artistName;
    private String shortDescription;
    private String description;

    private Integer creationYear;
    private String dimensions;

    @NotNull
    private BigDecimal price;

    private Long categoryId;
    private String space;
    private String frameType;
    private String shippingMethod;
    private BigDecimal shippingCost;

    private String thumbnailImageUrl;

    // getter/setter or Lombok @Getter @Setter
}
