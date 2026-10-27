package com.app.familhas_website.rating;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.familhas_website.client.ClientEntity;
import com.app.familhas_website.rating.dto.RatingRequest;
import com.app.familhas_website.rating.dto.RatingResponse;
import com.app.familhas_website.travelPackage.TravelPackageEntity;

import jakarta.persistence.EntityManager;

@Service
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;
    private final EntityManager entityManager;

    public RatingService(RatingRepository ratingRepository, EntityManager entityManager) {
        this.ratingRepository = ratingRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> findAll() {
        return ratingRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public RatingResponse findById(Long id) {
        return toResponse(getRatingOrThrow(id));
    }

    public RatingResponse create(RatingRequest request) {
        assertUniqueRatingPerClientAndPackage(request.clientId(), request.travelPackageId(), null);
        RatingEntity rating = new RatingEntity();
        apply(rating, request);
        return toResponse(ratingRepository.save(rating));
    }

    public RatingResponse update(Long id, RatingRequest request) {
        assertUniqueRatingPerClientAndPackage(request.clientId(), request.travelPackageId(), id);
        RatingEntity rating = getRatingOrThrow(id);
        apply(rating, request);
        return toResponse(ratingRepository.save(rating));
    }

    public void delete(Long id) {
        RatingEntity rating = getRatingOrThrow(id);
        ratingRepository.delete(rating);
    }

    private void assertUniqueRatingPerClientAndPackage(Long clientId, Long travelPackageId, Long excludeRatingId) {
        boolean duplicate;
        if (excludeRatingId == null) {
            duplicate = ratingRepository.existsByClient_IdAndTravelPackage_Id(clientId, travelPackageId);
        } else {
            duplicate = ratingRepository.existsByClient_IdAndTravelPackage_IdAndIdNot(
                    clientId, travelPackageId, excludeRatingId);
        }
        if (duplicate) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Client already rated this travel package");
        }
    }

    private RatingEntity getRatingOrThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found: " + id));
    }

    private <T> T getReferenceOrThrow(Class<T> entityClass, Long id, String label) {
        T entity = entityManager.find(entityClass, id);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, label + " not found: " + id);
        }
        return entity;
    }

    private void apply(RatingEntity rating, RatingRequest request) {
        rating.setRating(request.rating());
        rating.setClient(getReferenceOrThrow(ClientEntity.class, request.clientId(), "Client"));
        rating.setTravelPackage(
                getReferenceOrThrow(TravelPackageEntity.class, request.travelPackageId(), "Travel package"));
    }

    private RatingResponse toResponse(RatingEntity rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getRating(),
                rating.getClient().getId(),
                rating.getTravelPackage().getId());
    }
}



