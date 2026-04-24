package com.app.familhas_website.travelPackage;

import java.util.List;
import java.util.UUID;

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

import com.app.familhas_website.travelPackage.dto.TravelPackageRequest;
import com.app.familhas_website.travelPackage.dto.TravelPackageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/travel-packages")
public class TravelPackageController {

    private final TravelPackageService travelPackageService;

    public TravelPackageController(TravelPackageService travelPackageService) {
        this.travelPackageService = travelPackageService;
    }

    @GetMapping
    public List<TravelPackageResponse> findAll() {
        return travelPackageService.findAll();
    }

    @GetMapping("/{id}")
    public TravelPackageResponse findById(@PathVariable UUID id) {
        return travelPackageService.findById(id);
    }

    @PostMapping
    public ResponseEntity<TravelPackageResponse> create(@RequestBody @Valid TravelPackageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(travelPackageService.create(request));
    }

    @PutMapping("/{id}")
    public TravelPackageResponse update(@PathVariable UUID id, @RequestBody @Valid TravelPackageRequest request) {
        return travelPackageService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        travelPackageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}





