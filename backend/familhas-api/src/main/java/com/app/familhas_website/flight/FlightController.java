package com.app.familhas_website.flight;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.familhas_website.flight.dto.FlightRequest;
import com.app.familhas_website.flight.dto.FlightResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightResponse> findAll() {
        return flightService.findAll();
    }

    @GetMapping("/{id}")
    public FlightResponse findById(@PathVariable Long id) {
        return flightService.findById(id);
    }

    @PostMapping
    public ResponseEntity<FlightResponse> create(@RequestBody @Valid FlightRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(request));
    }

    @PutMapping("/{id}")
    public FlightResponse update(@PathVariable Long id, @RequestBody @Valid FlightRequest request) {
        return flightService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



