package br.com.familhas.rating.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequest(
        @NotNull
        @Min(1)
        @Max(5) Integer rating,
        @NotNull UUID clientId,
        @NotNull UUID travelPackageId) {
}



