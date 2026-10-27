package com.app.familhas_website.travelPackage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.app.familhas_website.category.CategoryEntity;
import com.app.familhas_website.city.CityEntity;
import com.app.familhas_website.enums.EntityStatus;
import com.app.familhas_website.flight.FlightEntity;
import com.app.familhas_website.rating.RatingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "travel_packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_travel_packages_generator")
    @SequenceGenerator(name = "seq_travel_packages_generator", sequenceName = "seq_travel_packages", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(precision = 12, scale = 2)
    private BigDecimal pricePromotion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EntityStatus status;

    @Column(length = 500)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_city_id", nullable = false)
    private CityEntity origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private CityEntity destination;

    @OneToMany(mappedBy = "travelPackage")
    @Builder.Default
    private List<RatingEntity> ratings = new ArrayList<>();
}
