package br.com.familhas.city.dto;

import java.util.UUID;

import br.com.familhas.enums.CityType;
import br.com.familhas.enums.EntityStatus;

public record CityResponse(
        UUID id,
        String name,
        String country,
        CityType cityType,
        EntityStatus status) {
}



