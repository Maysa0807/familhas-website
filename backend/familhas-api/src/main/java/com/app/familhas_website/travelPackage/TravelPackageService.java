package com.app.familhas_website.travelPackage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.familhas_website.category.CategoryEntity;
import com.app.familhas_website.city.CityEntity;
import com.app.familhas_website.flight.FlightEntity;
import com.app.familhas_website.travelPackage.dto.TravelPackageRequest;
import com.app.familhas_website.travelPackage.dto.TravelPackageResponse;

import jakarta.persistence.EntityManager;

@Service
@Transactional
public class TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;
    private final EntityManager entityManager;

    public TravelPackageService(TravelPackageRepository travelPackageRepository, EntityManager entityManager) {
        this.travelPackageRepository = travelPackageRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<TravelPackageResponse> findAll() {
        return travelPackageRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TravelPackageResponse findById(Long id) {
        return toResponse(getTravelPackageOrThrow(id));
    }

    public TravelPackageResponse create(TravelPackageRequest request) {
        validateTravelPackageBusinessRules(request);
        TravelPackageEntity travelPackage = new TravelPackageEntity();
        apply(travelPackage, request);
        return toResponse(travelPackageRepository.save(travelPackage));
    }

    public TravelPackageResponse update(Long id, TravelPackageRequest request) {
        validateTravelPackageBusinessRules(request);
        TravelPackageEntity travelPackage = getTravelPackageOrThrow(id);
        apply(travelPackage, request);
        return toResponse(travelPackageRepository.save(travelPackage));
    }

    public void delete(Long id) {
        TravelPackageEntity travelPackage = getTravelPackageOrThrow(id);
        travelPackageRepository.delete(travelPackage);
    }

    private void validateTravelPackageBusinessRules(TravelPackageRequest request) {
        if (request.originCityId().equals(request.destinationCityId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "originCityId and destinationCityId must be different");
        }
        BigDecimal promotion = request.pricePromotion();
        if (promotion != null) {
            if (promotion.compareTo(request.price()) >= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "pricePromotion must be less than price when provided");
            }
        }
    }

    private TravelPackageEntity getTravelPackageOrThrow(Long id) {
        return travelPackageRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel package not found: " + id));
    }

    private <T> T getReferenceOrThrow(Class<T> entityClass, Long id, String label) {
        T entity = entityManager.find(entityClass, id);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, label + " not found: " + id);
        }
        return entity;
    }

    private void apply(TravelPackageEntity travelPackage, TravelPackageRequest request) {
        travelPackage.setTitle(request.title().trim());
        travelPackage.setPrice(request.price());
        travelPackage.setPricePromotion(request.pricePromotion());
        travelPackage.setStatus(request.status());
        travelPackage.setImageUrl(blankToNull(request.imageUrl()));
        travelPackage.setFlight(getReferenceOrThrow(FlightEntity.class, request.flightId(), "Flight"));
        travelPackage.setCategory(getReferenceOrThrow(CategoryEntity.class, request.categoryId(), "Category"));
        travelPackage.setOrigin(getReferenceOrThrow(CityEntity.class, request.originCityId(), "Origin city"));
        travelPackage.setDestination(
                getReferenceOrThrow(CityEntity.class, request.destinationCityId(), "Destination city"));
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim();
    }

    private TravelPackageResponse toResponse(TravelPackageEntity travelPackage) {
        return new TravelPackageResponse(
                travelPackage.getId(),
                travelPackage.getTitle(),
                travelPackage.getPrice(),
                travelPackage.getPricePromotion(),
                travelPackage.getStatus(),
                travelPackage.getImageUrl(),
                travelPackage.getFlight().getId(),
                travelPackage.getCategory().getId(),
                travelPackage.getOrigin().getId(),
                travelPackage.getDestination().getId());
    }
}





