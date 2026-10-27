package com.app.familhas_website.client;

import com.app.familhas_website.client.dto.ClientRequest;
import com.app.familhas_website.client.dto.ClientResponse;
import com.app.familhas_website.travelPackage.TravelPackageEntity;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final EntityManager entityManager;

    public ClientService(ClientRepository clientRepository, EntityManager entityManager) {
        this.clientRepository = clientRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return toResponse(getClientOrThrow(id));
    }

    public ClientResponse create(ClientRequest request) {
        validateEmailUnique(request.email(), null);
        ClientEntity client = new ClientEntity();
        apply(client, request);
        return toResponse(clientRepository.save(client));
    }

    public ClientResponse update(Long id, ClientRequest request) {
        validateEmailUnique(request.email(), id);
        ClientEntity client = getClientOrThrow(id);
        apply(client, request);
        return toResponse(clientRepository.save(client));
    }

    public void delete(Long id) {
        ClientEntity client = getClientOrThrow(id);
        clientRepository.delete(client);
    }

    private void validateEmailUnique(String email, Long excludeClientId) {
        String normalized = email.trim().toLowerCase();
        boolean conflict;
        if (excludeClientId == null) {
            conflict = clientRepository.existsByEmailIgnoreCase(normalized);
        } else {
            conflict = clientRepository.existsByEmailIgnoreCaseAndIdNot(normalized, excludeClientId);
        }
        if (conflict) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
    }

    private ClientEntity getClientOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found: " + id));
    }

    private TravelPackageEntity getTravelPackageOrThrow(Long id) {
        TravelPackageEntity entity = entityManager.find(TravelPackageEntity.class, id);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel package not found: " + id);
        }
        return entity;
    }

    private List<TravelPackageEntity> mapTravelPackages(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<TravelPackageEntity> mapped = new ArrayList<>();
        for (Long id : ids) {
            mapped.add(getTravelPackageOrThrow(id));
        }
        return mapped;
    }

    private void apply(ClientEntity client, ClientRequest request) {
        client.setName(request.name().trim());
        client.setEmail(request.email().trim().toLowerCase());
        client.setPhoneNumber(blankToNull(request.phoneNumber()));
        client.setPassword(request.password());
        client.setStatus(request.status());
        client.setFavorites(mapTravelPackages(request.favoriteIds()));
        client.setMyFlights(mapTravelPackages(request.myFlightIds()));
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim();
    }

    private ClientResponse toResponse(ClientEntity client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getStatus(),
                client.getFavorites().stream().map(TravelPackageEntity::getId).toList(),
                client.getMyFlights().stream().map(TravelPackageEntity::getId).toList());
    }
}
