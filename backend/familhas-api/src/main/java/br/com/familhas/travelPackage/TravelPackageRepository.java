package br.com.familhas.travelPackage;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPackageRepository extends JpaRepository<TravelPackageEntity, UUID> {   
}