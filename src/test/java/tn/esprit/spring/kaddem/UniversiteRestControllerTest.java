package tn.esprit.spring.kaddem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.controllers.UniversiteRestController;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UniversiteRestControllerTest {

    @Mock
    private UniversiteServiceImpl universiteService;

    @InjectMocks
    private UniversiteRestController universiteRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(universiteRestController).build();
    }

    @Test
    void testGetAllUniversites() throws Exception {
        // Arrange
        Universite universite1 = new Universite(1, "Esprit");
        Universite universite2 = new Universite(2, "MIT");
        when(universiteService.retrieveAllUniversites()).thenReturn(Arrays.asList(universite1, universite2));

        // Act & Assert
        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUniv").value(1))
                .andExpect(jsonPath("$[0].nomUniv").value("Esprit"))
                .andExpect(jsonPath("$[1].idUniv").value(2))
                .andExpect(jsonPath("$[1].nomUniv").value("MIT"));

        verify(universiteService, times(1)).retrieveAllUniversites();
    }

    @Test
    void testGetUniversiteById() throws Exception {
        // Arrange
        Universite universite = new Universite(1, "Esprit");
        when(universiteService.retrieveUniversite(1)).thenReturn(universite);

        // Act & Assert
        mockMvc.perform(get("/universite/retrieve-universite/{universite-id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniv").value(1))
                .andExpect(jsonPath("$.nomUniv").value("Esprit"));

        verify(universiteService, times(1)).retrieveUniversite(1);
    }

    @Test
    void testGetUniversiteByIdNotFound() throws Exception {
        // Arrange: Universite with ID 1 doesn't exist
        when(universiteService.retrieveUniversite(1)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/universite/retrieve-universite/{universite-id}", 1))
                .andExpect(status().isNotFound());

        verify(universiteService, times(1)).retrieveUniversite(1);
    }

    @Test
    void testCreateUniversite() throws Exception {
        // Arrange
        Universite universite = new Universite(1, "Esprit");
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        // Act & Assert
        mockMvc.perform(post("/universite/add-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniv\": 1, \"nomUniv\": \"Esprit\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUniv").value(1))
                .andExpect(jsonPath("$.nomUniv").value("Esprit"));

        verify(universiteService, times(1)).addUniversite(any(Universite.class));
    }

    @Test
    void testUpdateUniversite() throws Exception {
        // Arrange
        Universite universite = new Universite(1, "Esprit");
        when(universiteService.updateUniversite(any(Universite.class))).thenReturn(universite);

        // Act & Assert
        mockMvc.perform(put("/universite/update-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniv\": 1, \"nomUniv\": \"Esprit\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniv").value(1))
                .andExpect(jsonPath("$.nomUniv").value("Esprit"));

        verify(universiteService, times(1)).updateUniversite(any(Universite.class));
    }

    @Test
    void testUpdateUniversiteNotFound() throws Exception {
        // Arrange
        when(universiteService.updateUniversite(any(Universite.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/universite/update-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniv\": 1, \"nomUniv\": \"Esprit\"}"))
                .andExpect(status().isNotFound());

        verify(universiteService, times(1)).updateUniversite(any(Universite.class));
    }

    @Test
    void testGetDepartementsByUniversite() throws Exception {
        // Arrange
        Set<Departement> departements = new HashSet<>();
        departements.add(new Departement(1, "Informatique"));

        when(universiteService.retrieveDepartementsByUniversite(1)).thenReturn(departements);

        // Act & Assert
        mockMvc.perform(get("/universite/listerDepartementsUniversite/{idUniversite}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDepart").value(1))
                .andExpect(jsonPath("$[0].nomDepart").value("Informatique"));

        verify(universiteService, times(1)).retrieveDepartementsByUniversite(1);
    }
}
