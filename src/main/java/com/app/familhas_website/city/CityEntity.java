package com.app.familhas_website.city;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;

import java.util.UUID;

@Entity
@Table(name = "cities")
@Builder
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CityType cityType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    CacheEntityLoaderHelper.EntityStatus status;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
