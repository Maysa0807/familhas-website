package br.com.familhas.city.dto;

import br.com.familhas.enums.CityType;
import br.com.familhas.enums.EntityStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CityRequest(
        @NotBlank
        @Size(max = 120) String name,
        @NotBlank
        @Size(max = 120) String country,
        @NotNull CityType cityType,
        @NotNull EntityStatus status) {
}



