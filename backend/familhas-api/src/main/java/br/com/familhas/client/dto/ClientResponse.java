package br.com.familhas.client.dto;

import br.com.familhas.enums.EntityStatus;

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
