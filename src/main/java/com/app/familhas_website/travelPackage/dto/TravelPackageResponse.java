package com.app.familhas_website.travelPackage.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.app.familhas_website.enums.EntityStatus;

public record TravelPackageResponse(
        UUID id,
        String title,
        BigDecimal price,
        BigDecimal pricePromotion,
        EntityStatus status,
        String imageUrl,
        UUID flightId,
        UUID categoryId,
        UUID originCityId,
        UUID destinationCityId) {
}





