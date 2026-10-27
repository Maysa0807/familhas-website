package br.com.familhas.category.dto;

import java.util.UUID;

import br.com.familhas.enums.EntityStatus;

public record CategoryResponse(
        UUID id,
        String title,
        EntityStatus status,
        String imageUrl,
        String description) {
}



