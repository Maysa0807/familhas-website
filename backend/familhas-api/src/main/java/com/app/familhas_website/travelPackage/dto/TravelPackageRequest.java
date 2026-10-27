package com.app.familhas_website.travelPackage.dto;

import java.math.BigDecimal;

import com.app.familhas_website.enums.EntityStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TravelPackageRequest(
        @NotBlank
        @Size(max = 180) String title,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal price,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal pricePromotion,
        @NotNull EntityStatus status,
        @Size(max = 500) String imageUrl,
        @NotNull Long flightId,
        @NotNull Long categoryId,
        @NotNull Long originCityId,
        @NotNull Long destinationCityId) {
}





