package br.com.familhas.rating;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, UUID> {

    boolean existsByClient_IdAndTravelPackage_Id(UUID clientId, UUID travelPackageId);

    boolean existsByClient_IdAndTravelPackage_IdAndIdNot(UUID clientId, UUID travelPackageId, UUID id);
}
