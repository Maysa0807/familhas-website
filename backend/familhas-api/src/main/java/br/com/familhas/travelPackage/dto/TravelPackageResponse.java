package br.com.familhas.travelPackage.dto;

import java.math.BigDecimal;

import br.com.familhas.enums.EntityStatus;

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





