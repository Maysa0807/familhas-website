package com.app.familhas_website.flight.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FlightRequest(
        @NotBlank
        @Size(max = 120) String companyName,
        @NotNull LocalDateTime boardingDate,
        @NotNull LocalDateTime returnDate,
        @NotBlank
        @Size(max = 120) String boardingAirport,
        @NotBlank
        @Size(max = 120) String returnAirport) {
}



