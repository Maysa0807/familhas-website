package br.com.familhas.travelPackage.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.familhas.enums.EntityStatus;

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





