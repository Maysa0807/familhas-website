package com.app.familhas_website.client;

import com.app.familhas_website.rating.RatingEntity;
import com.app.familhas_website.travelPackage.TravelPackageEntity;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Builder
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
    private CacheEntityLoaderHelper.EntityStatus status;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
