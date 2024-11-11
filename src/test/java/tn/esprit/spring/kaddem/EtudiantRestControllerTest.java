package tn.esprit.spring.kaddem;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 class EtudiantRestControllerTest {
     private ObjectMapper objectMapper = new ObjectMapper();
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

        Etudiant etudiant1 = new Etudiant( "John", "Doe");
        Etudiant etudiant2 = new Etudiant("Jane", "Doe");
        when(etudiantService.retrieveAllEtudiants()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        // Act & Assert
        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].nomE").value("John"))
                .andExpect(jsonPath("$[0].prenomE").value("Doe"))

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
    @Test
    void testDeleteEtudiant() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", 1))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).removeEtudiant(1);
    }

     @Test
     void testAffecterEtudiantToDepartement() throws Exception {
         // Arrange
         Etudiant etudiant = new Etudiant();
         etudiant.setIdEtudiant(1);
         etudiant.setNomE("John");
         etudiant.setPrenomE("Doe");

         // Simule que le service renvoie l'étudiant après l'affectation
         when(etudiantService.retrieveEtudiant(1)).thenReturn(etudiant);

         // Act & Assert
         mockMvc.perform(put("/etudiant/affecter-etudiant-departement/{etudiantId}/{departementId}", 1, 1))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.idEtudiant").value(1))
                 .andExpect(jsonPath("$.nomE").value("John"))
                 .andExpect(jsonPath("$.prenomE").value("Doe"));

         // Vérification que les méthodes assignEtudiantToDepartement et retrieveEtudiant ont été appelées
         verify(etudiantService, times(1)).assignEtudiantToDepartement(1, 1);
         verify(etudiantService, times(1)).retrieveEtudiant(1);
     }
     @Test
     void testAddEtudiantWithEquipeAndContract() throws Exception {
         // Arrange
         EtudiantDTO etudiantDTO = new EtudiantDTO();
         etudiantDTO.setNomE("John");
         etudiantDTO.setPrenomE("Doe");

         Etudiant etudiant = new Etudiant();
         etudiant.setIdEtudiant(1);
         etudiant.setNomE("John");
         etudiant.setPrenomE("Doe");

         // Simule le comportement du service pour ajouter et affecter l'étudiant
         when(etudiantService.addAndAssignEtudiantToEquipeAndContract(any(Etudiant.class), eq(1), eq(2)))
                 .thenReturn(etudiant);

         // Act & Assert
         mockMvc.perform(post("/etudiant/add-assign-Etudiant/{idContrat}/{idEquipe}", 1, 2)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(etudiantDTO)))
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.idEtudiant").value(1))
                 .andExpect(jsonPath("$.nomE").value("John"))
                 .andExpect(jsonPath("$.prenomE").value("Doe"));

         // Vérification que la méthode a bien été appelée
         verify(etudiantService, times(1)).addAndAssignEtudiantToEquipeAndContract(any(Etudiant.class), eq(1), eq(2));
     }

     @Test
     void testAddEtudiantWithEquipeAndContract_shouldReturnBadRequest_whenInvalidInput() throws Exception {
         // Arrange
         EtudiantDTO invalidEtudiantDTO = new EtudiantDTO();  // No required fields set

         // Act & Assert
         mockMvc.perform(post("/etudiant/add-assign-Etudiant/{idContrat}/{idEquipe}", 1, 2)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(invalidEtudiantDTO)))
                 .andExpect(status().isBadRequest());
     }

     @Test
     void testGetEtudiantsParDepartement_shouldReturnNotFound_whenNoEtudiants() throws Exception {
         // Simule le comportement du service pour retourner une liste vide
         when(etudiantService.getEtudiantsByDepartement(1)).thenReturn(Collections.emptyList());

         // Act & Assert
         mockMvc.perform(get("/etudiant/getEtudiantsByDepartement/{idDepartement}", 1))
                 .andExpect(status().isNotFound());
     }

     @Test
     void testAffecterEtudiantToDepartement_shouldReturnNotFound_whenEtudiantNotFound() throws Exception {
         // Simuler que l'étudiant n'existe pas
         doThrow(new EntityNotFoundException("Etudiant not found"))
                 .when(etudiantService).assignEtudiantToDepartement(999, 1);

         // Act & Assert
         mockMvc.perform(put("/etudiant/affecter-etudiant-departement/{etudiantId}/{departementId}", 999, 1))
                 .andExpect(status().isNotFound());
     }

     @Test
     void testRemoveEtudiant_shouldReturnNotFound_whenEtudiantDoesNotExist() throws Exception {
         // Simuler que l'étudiant n'existe pas
         doThrow(new EntityNotFoundException("Etudiant not found"))
                 .when(etudiantService).removeEtudiant(999);

         // Act & Assert
         mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", 999))
                 .andExpect(status().isNotFound());
     }
}
