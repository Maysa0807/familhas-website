package com.app.familhas_website.city;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.familhas_website.city.dto.CityRequest;
import com.app.familhas_website.city.dto.CityResponse;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public List<CityResponse> findAll() {
        return cityRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CityResponse findById(Long id) {
        return toResponse(getCityOrThrow(id));
    }

    public CityResponse create(CityRequest request) {
        CityEntity city = new CityEntity();
        apply(city, request);
        return toResponse(cityRepository.save(city));
    }

    public CityResponse update(Long id, CityRequest request) {
        CityEntity city = getCityOrThrow(id);
        apply(city, request);
        return toResponse(cityRepository.save(city));
    }

    public void delete(Long id) {
        CityEntity city = getCityOrThrow(id);
        cityRepository.delete(city);
    }

    private CityEntity getCityOrThrow(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found: " + id));
    }

    private void apply(CityEntity city, CityRequest request) {
        city.setName(request.name().trim());
        city.setCountry(request.country().trim());
        city.setCityType(request.cityType());
        city.setStatus(request.status());
    }

    private CityResponse toResponse(CityEntity city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getCityType(),
                city.getStatus());
    }
}



