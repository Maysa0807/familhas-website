package com.app.familhas_website.flight.dto;

import java.time.LocalDateTime;

public record FlightResponse(
        Long id,
        String companyName,
        LocalDateTime boardingDate,
        LocalDateTime returnDate,
        String boardingAirport,
        String returnAirport) {
}



