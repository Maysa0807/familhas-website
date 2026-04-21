package com.app.familhas_website.travelPackage;

import com.app.familhas_website.category.CategoryEntity;
import com.app.familhas_website.city.CityEntity;
import com.app.familhas_website.flight.FlightEntity;
import com.app.familhas_website.rating.RatingEntity;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "travel_packages")
@Builder
public class TravelPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(precision = 12, scale = 2)
    private BigDecimal pricePromotion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CacheEntityLoaderHelper.EntityStatus status;

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
    @JoinColumn(name = "destintation_city_id", nullable = false)
    private CityEntity destination;

    @OneToMany(mappedBy = "travelPackage")
    @Builder.Default
    private List<RatingEntity> ratings = new ArrayList<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPricePromotion() {
        return pricePromotion;
    }

    public void setPricePromotion(BigDecimal pricePromotion) {
        this.pricePromotion = pricePromotion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
