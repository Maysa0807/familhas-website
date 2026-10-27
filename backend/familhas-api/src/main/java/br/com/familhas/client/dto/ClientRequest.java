package br.com.familhas.client.dto;

import br.com.familhas.enums.EntityStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

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
        List<UUID> favoriteIds,
        List<UUID> myFlightIds) {

    public ClientRequest {
        favoriteIds = favoriteIds == null ? List.of() : List.copyOf(favoriteIds);
        myFlightIds = myFlightIds == null ? List.of() : List.copyOf(myFlightIds);

    }
}
