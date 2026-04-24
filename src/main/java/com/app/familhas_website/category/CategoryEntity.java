package com.app.familhas_website.category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.app.familhas_website.enums.EntityStatus;
import com.app.familhas_website.travelPackage.TravelPackageEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EntityStatus status;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<TravelPackageEntity> travelPackages = new ArrayList<>();
}

