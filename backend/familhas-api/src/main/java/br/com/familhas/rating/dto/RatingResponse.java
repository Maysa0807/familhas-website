package br.com.familhas.rating.dto;

import java.util.UUID;

public record RatingResponse(
        UUID id,
        Integer rating,
        UUID clientId,
        UUID travelPackageId) {
}



