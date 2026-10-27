package br.com.familhas.flight.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FlightResponse(
        UUID id,
        String companyName,
        LocalDateTime boardingDate,
        LocalDateTime returnDate,
        String boardingAirport,
        String returnAirport) {
}



