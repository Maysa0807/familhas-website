package com.app.familhas_website.flight;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.familhas_website.flight.dto.FlightRequest;
import com.app.familhas_website.flight.dto.FlightResponse;

@Service
@Transactional
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Transactional(readOnly = true)
    public List<FlightResponse> findAll() {
        return flightRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public FlightResponse findById(Long id) {
        return toResponse(getFlightOrThrow(id));
    }

    public FlightResponse create(FlightRequest request) {
        validateFlightSchedule(request);
        FlightEntity flight = new FlightEntity();
        apply(flight, request);
        return toResponse(flightRepository.save(flight));
    }

    public FlightResponse update(Long id, FlightRequest request) {
        validateFlightSchedule(request);
        FlightEntity flight = getFlightOrThrow(id);
        apply(flight, request);
        return toResponse(flightRepository.save(flight));
    }

    public void delete(Long id) {
        FlightEntity flight = getFlightOrThrow(id);
        flightRepository.delete(flight);
    }

    private static void validateFlightSchedule(FlightRequest request) {
        if (!request.returnDate().isAfter(request.boardingDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "returnDate must be after boardingDate");
        }
    }

    private FlightEntity getFlightOrThrow(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found: " + id));
    }

    private void apply(FlightEntity flight, FlightRequest request) {
        flight.setCompanyName(request.companyName().trim());
        flight.setBoardingDate(request.boardingDate());
        flight.setReturnDate(request.returnDate());
        flight.setBoardingAirport(request.boardingAirport().trim());
        flight.setReturnAirport(request.returnAirport().trim());
    }

    private FlightResponse toResponse(FlightEntity flight) {
        return new FlightResponse(
                flight.getId(),
                flight.getCompanyName(),
                flight.getBoardingDate(),
                flight.getReturnDate(),
                flight.getBoardingAirport(),
                flight.getReturnAirport());
    }
}



