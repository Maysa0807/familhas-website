package com.app.familhas_website.category.dto;

import com.app.familhas_website.enums.EntityStatus;

public record CategoryResponse(
        Long id,
        String title,
        EntityStatus status,
        String imageUrl,
        String description) {
}



