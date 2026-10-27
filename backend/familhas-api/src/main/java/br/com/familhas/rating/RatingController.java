package br.com.familhas.rating;

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

import br.com.familhas.rating.dto.RatingRequest;
import br.com.familhas.rating.dto.RatingResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<RatingResponse> findAll() {
        return ratingService.findAll();
    }

    @GetMapping("/{id}")
    public RatingResponse findById(@PathVariable UUID id) {
        return ratingService.findById(id);
    }

    @PostMapping
    public ResponseEntity<RatingResponse> create(@RequestBody @Valid RatingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(request));
    }

    @PutMapping("/{id}")
    public RatingResponse update(@PathVariable UUID id, @RequestBody @Valid RatingRequest request) {
        return ratingService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



