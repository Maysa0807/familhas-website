package com.app.familhas_website.flight;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flights_generator")
    @SequenceGenerator(name = "seq_flights_generator", sequenceName = "seq_flights", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 120)
    private String companyName;

    @Column(nullable = false)
    private LocalDateTime boardingDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @Column(nullable = false, length = 120)
    private String boardingAirport;

    @Column(nullable = false, length = 120)
    private String returnAirport;
}
