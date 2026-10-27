package br.com.familhas.rating;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.familhas.client.ClientEntity;
import br.com.familhas.rating.dto.RatingRequest;
import br.com.familhas.rating.dto.RatingResponse;
import br.com.familhas.travelPackage.TravelPackageEntity;

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
    public RatingResponse findById(UUID id) {
        return toResponse(getRatingOrThrow(id));
    }

    public RatingResponse create(RatingRequest request) {
        assertUniqueRatingPerClientAndPackage(request.clientId(), request.travelPackageId(), null);
        RatingEntity rating = new RatingEntity();
        apply(rating, request);
        return toResponse(ratingRepository.save(rating));
    }

    public RatingResponse update(UUID id, RatingRequest request) {
        assertUniqueRatingPerClientAndPackage(request.clientId(), request.travelPackageId(), id);
        RatingEntity rating = getRatingOrThrow(id);
        apply(rating, request);
        return toResponse(ratingRepository.save(rating));
    }

    public void delete(UUID id) {
        RatingEntity rating = getRatingOrThrow(id);
        ratingRepository.delete(rating);
    }

    private void assertUniqueRatingPerClientAndPackage(UUID clientId, UUID travelPackageId, UUID excludeRatingId) {
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

    private RatingEntity getRatingOrThrow(UUID id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found: " + id));
    }

    private <T> T getReferenceOrThrow(Class<T> entityClass, UUID id, String label) {
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



