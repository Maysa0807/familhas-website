package br.com.familhas.rating.dto;

public record RatingResponse(
        Long id,
        Integer rating,
        Long clientId,
        Long travelPackageId) {
}



