package com.app.familhas_website.travelPackage.dto;

import java.math.BigDecimal;

import com.app.familhas_website.enums.EntityStatus;

public record TravelPackageResponse(
        Long id,
        String title,
        BigDecimal price,
        BigDecimal pricePromotion,
        EntityStatus status,
        String imageUrl,
        Long flightId,
        Long categoryId,
        Long originCityId,
        Long destinationCityId) {
}





