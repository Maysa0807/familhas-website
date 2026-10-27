package br.com.familhas.city;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.familhas.city.dto.CityResponse;
import br.com.familhas.config.SecurityConfig;
import br.com.familhas.enums.CityType;
import br.com.familhas.enums.EntityStatus;

@WebMvcTest(CityController.class)
@Import(SecurityConfig.class)
class CityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CityService cityService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void findByIdAcceptsUuidPathVariable() throws Exception {
        UUID id = UUID.fromString("c1770000-0000-4000-8000-000000000001");
        when(cityService.findById(id))
                .thenReturn(new CityResponse(id, "Sao Paulo", "Brasil", CityType.ORIGIN, EntityStatus.ACTIVE));

        mockMvc.perform(get("/cities/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Sao Paulo"));
    }

    @Test
    void findByIdRejectsLegacyNumericId() throws Exception {
        mockMvc.perform(get("/cities/{id}", "1"))
                .andExpect(status().isBadRequest());

        verify(cityService, never()).findById(any());
    }
}
