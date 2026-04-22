package com.app.familhas_website.city;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;

import java.util.UUID;

@Entity
@Table(name = "cities")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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



}
