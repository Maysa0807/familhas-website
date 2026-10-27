package br.com.familhas.category.dto;

import br.com.familhas.enums.EntityStatus;

public record CategoryResponse(
        Long id,
        String title,
        EntityStatus status,
        String imageUrl,
        String description) {
}



