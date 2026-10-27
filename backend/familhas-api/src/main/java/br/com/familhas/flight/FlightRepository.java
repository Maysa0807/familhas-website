package br.com.familhas.flight;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, UUID> {
}
