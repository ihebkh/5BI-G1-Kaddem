package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.services.IContratService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
        // Arrange
        Contrat contrat1 = new Contrat();
        contrat1.setIdContrat(1);
        Contrat contrat2 = new Contrat();
        contrat2.setIdContrat(2);

        List<Contrat> expectedContrats = Arrays.asList(contrat1, contrat2);
        when(contratService.retrieveAllContrats()).thenReturn(expectedContrats);

        // Act
        List<Contrat> actualContrats = contratRestController.getContrats();

        // Assert
        assertEquals(expectedContrats, actualContrats);
        verify(contratService, times(1)).retrieveAllContrats();
    }

    @Test
    void testRetrieveContrat() {
        // Arrange
        Integer contratId = 1;
        Contrat expectedContrat = new Contrat();
        expectedContrat.setIdContrat(contratId);
        when(contratService.retrieveContrat(contratId)).thenReturn(expectedContrat);

        // Act
        Contrat actualContrat = contratRestController.retrieveContrat(contratId);

        // Assert
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).retrieveContrat(contratId);
    }

    @Test
    void testRemoveContrat() {
        // Arrange
        Integer contratId = 1;
        doNothing().when(contratService).removeContrat(contratId);

        // Act
        contratRestController.removeContrat(contratId);

        // Assert
        verify(contratService, times(1)).removeContrat(contratId);
    }

    @Test
    void testAssignContratToEtudiant() {
        // Arrange
        Integer idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";
        Contrat expectedContrat = new Contrat();
        expectedContrat.setIdContrat(idContrat);
        when(contratService.affectContratToEtudiant(idContrat, nomE, prenomE)).thenReturn(expectedContrat);

        // Act
        Contrat actualContrat = contratRestController.assignContratToEtudiant(idContrat, nomE, prenomE);

        // Assert
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).affectContratToEtudiant(idContrat, nomE, prenomE);
    }

    @Test
    void testMajStatusContrat() {
        // Arrange
        doNothing().when(contratService).retrieveAndUpdateStatusContrat();

        // Act
        contratRestController.majStatusContrat();

        // Assert
        verify(contratService, times(1)).retrieveAndUpdateStatusContrat();
    }

    @Test
    void testAddContrat() throws ParseException {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateDebut = dateFormat.parse("2024-10-12T13:00:51.812Z");
        Date dateFin = dateFormat.parse("2024-10-12T13:00:51.812Z");

        Contrat contratInput = new Contrat();
        contratInput.setIdContrat(822);
        contratInput.setDateDebutContrat(dateDebut);
        contratInput.setDateFinContrat(dateFin);
        contratInput.setSpecialite(Specialite.IA);  // Assurez-vous d'avoir l'enum Specialite
        contratInput.setArchive(true);
        contratInput.setMontantContrat(0);

        // Act
        Contrat result = contratRestController.addContrat(contratInput);

        // Assert
        assertEquals(contratInput.getIdContrat(), result.getIdContrat());
        assertEquals(contratInput.getDateDebutContrat(), result.getDateDebutContrat());
        assertEquals(contratInput.getDateFinContrat(), result.getDateFinContrat());
        assertEquals(contratInput.getSpecialite(), result.getSpecialite());
        assertEquals(contratInput.getArchive(), result.getArchive());
        assertEquals(contratInput.getMontantContrat(), result.getMontantContrat());
    }

}
