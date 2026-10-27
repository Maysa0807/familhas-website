package com.app.familhas_website.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequest(
        @NotNull
        @Min(1)
        @Max(5) Integer rating,
        @NotNull Long clientId,
        @NotNull Long travelPackageId) {
}



