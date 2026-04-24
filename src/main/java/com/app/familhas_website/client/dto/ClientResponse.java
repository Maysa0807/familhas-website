package com.app.familhas_website.client.dto;

import com.app.familhas_website.enums.EntityStatus;

import java.util.List;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        EntityStatus status,
        List<Long> favoriteIds,
        List<Long> myFlightIds) {

    }
