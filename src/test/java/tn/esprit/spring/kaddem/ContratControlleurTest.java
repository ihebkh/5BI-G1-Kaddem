package tn.esprit.spring.kaddem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.ContratDTO;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.services.IContratService;
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
        // Initialiser le DTO de contrat
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setDateDebutContrat("2024-01-01");
        contratDTO.setDateFinContrat("2024-12-31");
        contratDTO.setSpecialite("INFORMATIQUE");
        contratDTO.setArchive(false);
        contratDTO.setMontantContrat(1000);

        // Mock du contrat ajouté
        Contrat expectedContrat = new Contrat();
        expectedContrat.setIdContrat(1);
        when(contratService.addContrat(any(Contrat.class))).thenReturn(expectedContrat);

        // Appel de la méthode de test
        Contrat actualContrat = contratRestController.addContrat(contratDTO);

        // Vérifications
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).addContrat(any(Contrat.class));
    }

}
