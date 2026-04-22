package com.app.familhas_website.category;

import com.app.familhas_website.travelPackage.TravelPackageEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CacheEntityLoaderHelper.EntityStatus status;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<TravelPackageEntity> travelPackages = new ArrayList<>();


}
