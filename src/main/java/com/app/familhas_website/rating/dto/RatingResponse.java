package com.app.familhas_website.rating.dto;

public record RatingResponse(
        Long id,
        Integer rating,
        Long clientId,
        Long travelPackageId) {
}



