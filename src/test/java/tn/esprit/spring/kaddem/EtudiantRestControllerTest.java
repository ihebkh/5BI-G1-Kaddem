package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.kaddem.controllers.EtudiantRestController;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EtudiantRestControllerTest {
    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantRestController).build();
    }
    @Test
    void testGetAllEtudiants() throws Exception {
        // Arrange
       // Etudiant etudiant1 = new Etudiant(1, "John", "Doe");
        //Etudiant etudiant2 = new Etudiant(2, "Jane", "Doe");
        Etudiant etudiant1 = new Etudiant( "John", "Doe");
        Etudiant etudiant2 = new Etudiant("Jane", "Doe");
        when(etudiantService.retrieveAllEtudiants()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$[0].idEtudiant").value(1))
                .andExpect(jsonPath("$[0].nomE").value("John"))
                .andExpect(jsonPath("$[0].prenomE").value("Doe"))
                //.andExpect(jsonPath("$[1].idEtudiant").value(2))
                .andExpect(jsonPath("$[1].nomE").value("Jane"))
                .andExpect(jsonPath("$[1].prenomE").value("Doe"));

        verify(etudiantService, times(1)).retrieveAllEtudiants();
    }

    @Test
    void testGetEtudiantById() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1); // Assurez-vous que le setter est utilisé correctement
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        when(etudiantService.retrieveEtudiant(1)).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-etudiant/{etudiant-id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1)) // Correspond à l'attribut de l'objet
                .andExpect(jsonPath("$.nomE").value("John"))
                .andExpect(jsonPath("$.prenomE").value("Doe"));

        verify(etudiantService, times(1)).retrieveEtudiant(1);
    }

    @Test
    void testGetEtudiantByIdNotFound() throws Exception {
        // Arrange
        when(etudiantService.retrieveEtudiant(1)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-etudiant/{etudiant-id}", 1))
                .andExpect(status().isNotFound());

        verify(etudiantService, times(1)).retrieveEtudiant(1);
    }
    @Test
    void testCreateEtudiant() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1); // Assurez-vous que l'ID est bien défini
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomE\": \"John\", \"prenomE\": \"Doe\"}")) // Ne pas définir l'ID dans le JSON (généralement défini par le système)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEtudiant").value(1)) // Correspond à l'objet renvoyé après création
                .andExpect(jsonPath("$.nomE").value("John"))
                .andExpect(jsonPath("$.prenomE").value("Doe"));

        verify(etudiantService, times(1)).addEtudiant(any(Etudiant.class));
    }

    @Test
    void testUpdateEtudiant() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1); // Assurez-vous que l'ID est bien défini
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");

        when(etudiantService.updateEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(put("/etudiant/update-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idEtudiant\": 1, \"nomE\": \"John\", \"prenomE\": \"Doe\"}")) // Inclure l'ID dans la requête
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1)) // L'ID est attendu dans la réponse
                .andExpect(jsonPath("$.nomE").value("John"))
                .andExpect(jsonPath("$.prenomE").value("Doe"));

        verify(etudiantService, times(1)).updateEtudiant(any(Etudiant.class));
    }

    @Test
    void testUpdateEtudiantNotFound() throws Exception {
        // Arrange
        when(etudiantService.updateEtudiant(any(Etudiant.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/etudiant/update-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idEtudiant\": 1, \"nomE\": \"John\", \"prenomE\": \"Doe\"}")) // Inclure l'ID ici aussi
                .andExpect(status().isNotFound());

        verify(etudiantService, times(1)).updateEtudiant(any(Etudiant.class));
    }
   /* @Test
    void testDeleteEtudiant() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", 1))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).removeEtudiant(1);
    }*/

   /* @Test
    void testAffecterEtudiantToDepartement() throws Exception {
        // Act & Assert
        mockMvc.perform(put("/etudiant/affecter-etudiant-departement/{etudiantId}/{departementId}", 1, 1))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).assignEtudiantToDepartement(1, 1);
    }*/
}
