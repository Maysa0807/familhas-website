package com.app.familhas_website.client;

import com.app.familhas_website.enums.EntityStatus;
import com.app.familhas_website.rating.RatingEntity;
import com.app.familhas_website.travelPackage.TravelPackageEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_clients_generator")
    @SequenceGenerator(name = "seq_clients_generator", sequenceName = "seq_clients", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 180)
    private String name;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @Column(length = 40)
    private String phoneNumber;

    @Column(nullable = false, length = 225)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EntityStatus status;

    @ManyToMany
    @JoinTable(
            name = "client_favorites",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_package_id"))
    @Builder.Default
    private List<TravelPackageEntity> favorites = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "client_flights",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_package_id"))
    @Builder.Default
    private List<TravelPackageEntity> myFlights = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    @Builder.Default
    private List<RatingEntity> ratings = new ArrayList<>();


}
