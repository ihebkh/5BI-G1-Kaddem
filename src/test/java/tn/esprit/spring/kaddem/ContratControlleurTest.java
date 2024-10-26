package tn.esprit.spring.kaddem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.ContratDTO;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.services.IContratService;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContratControlleurTest {

    @Mock
    private IContratService contratService;

    @InjectMocks
    private ContratRestController contratRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetContrats() {

        Contrat contrat1 = new Contrat();
        contrat1.setIdContrat(1);
        Contrat contrat2 = new Contrat();
        contrat2.setIdContrat(2);
        List<Contrat> expectedContrats = Arrays.asList(contrat1, contrat2);
        when(contratService.retrieveAllContrats()).thenReturn(expectedContrats);
        List<Contrat> actualContrats = contratRestController.getContrats();
        assertEquals(expectedContrats, actualContrats);
        verify(contratService, times(1)).retrieveAllContrats();
    }

    @Test
    void testRetrieveContrat() {
        Integer contratId = 1;
        Contrat expectedContrat = new Contrat();
        expectedContrat.setIdContrat(contratId);
        when(contratService.retrieveContrat(contratId)).thenReturn(expectedContrat);
        Contrat actualContrat = contratRestController.retrieveContrat(contratId);
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).retrieveContrat(contratId);
    }

    @Test
    void testRemoveContrat() {
        Integer contratId = 1;
        doNothing().when(contratService).removeContrat(contratId);
        contratRestController.removeContrat(contratId);
        verify(contratService, times(1)).removeContrat(contratId);
    }

    @Test
    void testAssignContratToEtudiant() {
        Integer idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";
        Contrat expectedContrat = new Contrat();
        expectedContrat.setIdContrat(idContrat);
        when(contratService.affectContratToEtudiant(idContrat, nomE, prenomE)).thenReturn(expectedContrat);
        Contrat actualContrat = contratRestController.assignContratToEtudiant(idContrat, nomE, prenomE);
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).affectContratToEtudiant(idContrat, nomE, prenomE);
    }

    @Test
    void testMajStatusContrat() {
        doNothing().when(contratService).retrieveAndUpdateStatusContrat();
        contratRestController.majStatusContrat();
        verify(contratService, times(1)).retrieveAndUpdateStatusContrat();
    }

    @Test
     void testAddContrat() {
        // Création de l'objet ContratDTO pour le test
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setDateDebutContrat("2024-10-26");
        contratDTO.setDateFinContrat("2025-10-26");
        contratDTO.setSpecialite("RESEAUX");
        contratDTO.setArchive(false);
        contratDTO.setMontantContrat(1500);

        // Création de l'objet Contrat attendu
        Contrat expectedContrat = new Contrat();
        expectedContrat.setDateDebutContrat(Date.valueOf("2024-10-26"));
        expectedContrat.setDateFinContrat(Date.valueOf("2025-10-26"));
        expectedContrat.setSpecialite(Specialite.RESEAUX);
        expectedContrat.setArchive(false);
        expectedContrat.setMontantContrat(1500);

        // Simulation du comportement du service
        when(contratService.addContrat(any(Contrat.class))).thenReturn(expectedContrat);

        // Appel de la méthode et vérification
        Contrat result = contratRestController.addContrat(contratDTO);
        assertEquals(expectedContrat, result);
    }

    @Test
     void testUpdateContrat() {
        // ID du contrat à mettre à jour
        Integer contratId = 1;

        // Création de l'objet ContratDTO avec les nouvelles valeurs
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setDateDebutContrat("2024-11-01");
        contratDTO.setDateFinContrat("2025-11-01");
        contratDTO.setSpecialite("RESEAUX");
        contratDTO.setArchive(true);
        contratDTO.setMontantContrat(2000);

        // Création de l'objet Contrat attendu après la mise à jour
        Contrat updatedContrat = new Contrat();
        updatedContrat.setIdContrat(contratId);
        updatedContrat.setDateDebutContrat(Date.valueOf("2024-11-01"));
        updatedContrat.setDateFinContrat(Date.valueOf("2025-11-01"));
        updatedContrat.setSpecialite(Specialite.RESEAUX);
        updatedContrat.setArchive(true);
        updatedContrat.setMontantContrat(2000);

        // Simulation du comportement du service
        when(contratService.updateContrat(any(Contrat.class))).thenReturn(updatedContrat);

        // Appel de la méthode et vérification
        Contrat result = contratRestController.updateContrat(contratId, contratDTO);
        assertEquals(updatedContrat, result);
    }

}
