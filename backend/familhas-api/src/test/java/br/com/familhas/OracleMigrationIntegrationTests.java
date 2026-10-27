package br.com.familhas;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

import br.com.familhas.category.CategoryRepository;
import br.com.familhas.city.CityEntity;
import br.com.familhas.city.CityRepository;
import br.com.familhas.client.ClientRepository;
import br.com.familhas.enums.CityType;
import br.com.familhas.enums.EntityStatus;
import br.com.familhas.flight.FlightRepository;
import br.com.familhas.rating.RatingRepository;
import br.com.familhas.travelPackage.TravelPackageService;
import br.com.familhas.travelPackage.TravelPackageRepository;
import br.com.familhas.travelPackage.dto.TravelPackageResponse;

/**
 * Sobe um Oracle real, aplica as migrations V1..V4 via Flyway e valida o
 * mapeamento JPA (ddl-auto=validate no profile local). Pulado sem Docker.
 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class OracleMigrationIntegrationTests {

    // Timeout padrao do OracleContainer e 60 s, curto para Docker Desktop recem-iniciado no Windows.
    @Container
    @ServiceConnection
    static OracleContainer oracle = new OracleContainer("gvenzl/oracle-free:23-slim-faststart")
            .withStartupTimeout(Duration.ofMinutes(5));

    private static final UUID PACKAGE_MACEIO = UUID.fromString("bac00000-0000-4000-8000-000000000001");
    private static final UUID FLIGHT_GOL = UUID.fromString("f1170000-0000-4000-8000-000000000001");
    private static final UUID CITY_SAO_PAULO = UUID.fromString("c1770000-0000-4000-8000-000000000001");
    private static final UUID CITY_MACEIO = UUID.fromString("c1770000-0000-4000-8000-000000000002");

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TravelPackageService travelPackageService;

    @Test
    void seedIsReappliedWithUuidIds() {
        assertThat(categoryRepository.count()).isEqualTo(3);
        assertThat(cityRepository.count()).isEqualTo(6);
        assertThat(flightRepository.count()).isEqualTo(3);
        assertThat(travelPackageRepository.count()).isEqualTo(2);
        assertThat(clientRepository.count()).isEqualTo(2);
        assertThat(ratingRepository.count()).isEqualTo(2);
    }

    @Test
    void seedUuidsMatchDocumentedValuesAndRelationships() {
        TravelPackageResponse response = travelPackageService.findById(PACKAGE_MACEIO);

        assertThat(response.title()).isEqualTo("Maceio Resort All Inclusive - 7 noites");
        assertThat(response.flightId()).isEqualTo(FLIGHT_GOL);
        assertThat(response.originCityId()).isEqualTo(CITY_SAO_PAULO);
        assertThat(response.destinationCityId()).isEqualTo(CITY_MACEIO);
    }

    @Test
    void newEntitiesReceiveGeneratedUuid() {
        CityEntity saved = cityRepository.saveAndFlush(CityEntity.builder()
                .name("Lisboa")
                .country("Portugal")
                .cityType(CityType.DESTINATION)
                .status(EntityStatus.ACTIVE)
                .build());

        assertThat(saved.getId()).isNotNull();
        assertThat(cityRepository.findById(saved.getId()))
                .get()
                .extracting(CityEntity::getName)
                .isEqualTo("Lisboa");
    }
}
