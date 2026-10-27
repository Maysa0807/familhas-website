package com.app.familhas_website.city.dto;

import com.app.familhas_website.enums.CityType;
import com.app.familhas_website.enums.EntityStatus;

public record CityResponse(
        Long id,
        String name,
        String country,
        CityType cityType,
        EntityStatus status) {
}



