package br.com.familhas.category.dto;

import br.com.familhas.enums.EntityStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank
        @Size(max = 120) String title,
        @NotNull EntityStatus status,
        @Size(max = 500) String imageUrl,
        @Size(max = 1000) String description) {
}



