package com.app.familhas_website.client.dto;

import com.app.familhas_website.enums.EntityStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ClientRequest (
        @NotBlank
        @Size(max = 180)String name,
        @NotBlank
        @Email
        @Size(max = 180) String email,
        @Size(max = 40) String phoneNumber,
        @NotBlank
        @Size(min = 8, max = 255) String password,
        @NotNull EntityStatus status,
        List<Long> favoriteIds,
        List<Long> myFlightIds) {

    public ClientRequest {
        favoriteIds = favoriteIds == null ? List.of() : List.copyOf(favoriteIds);
        myFlightIds = myFlightIds == null ? List.of() : List.copyOf(myFlightIds);

    }
}
