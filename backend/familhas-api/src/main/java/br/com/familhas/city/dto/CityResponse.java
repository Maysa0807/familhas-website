package br.com.familhas.city.dto;

import br.com.familhas.enums.CityType;
import br.com.familhas.enums.EntityStatus;

public record CityResponse(
        Long id,
        String name,
        String country,
        CityType cityType,
        EntityStatus status) {
}



