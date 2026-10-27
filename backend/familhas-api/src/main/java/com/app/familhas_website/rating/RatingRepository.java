package com.app.familhas_website.rating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    boolean existsByClient_IdAndTravelPackage_Id(Long clientId, Long travelPackageId);

    boolean existsByClient_IdAndTravelPackage_IdAndIdNot(Long clientId, Long travelPackageId, Long id);
}
