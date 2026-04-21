package com.app.familhas_website.flight;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flights")
@Builder
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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




    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getBoardingDate() {
        return boardingDate;
    }

    public void setBoardingDate(LocalDateTime boardingDate) {
        this.boardingDate = boardingDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getBoardingAirport() {
        return boardingAirport;
    }

    public void setBoardingAirport(String boardingAirport) {
        this.boardingAirport = boardingAirport;
    }

    public String getReturnAirport() {
        return returnAirport;
    }

    public void setReturnAirport(String returnAirport) {
        this.returnAirport = returnAirport;
    }

}
