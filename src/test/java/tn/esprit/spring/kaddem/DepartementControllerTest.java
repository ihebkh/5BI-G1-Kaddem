package tn.esprit.spring.kaddem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.controllers.DepartementRestController;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.services.IDepartementService;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartementControllerTest {

    @Mock
    private IDepartementService departementService;

    @InjectMocks
    private DepartementRestController departementRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDepartements() {
        Departement departement1 = new Departement();
        departement1.setIdDepart(1);
        Departement departement2 = new Departement();
        departement2.setIdDepart(2);
        List<Departement> expectedDepartements = Arrays.asList(departement1, departement2);
        when(departementService.retrieveAllDepartements()).thenReturn(expectedDepartements);
        List<Departement> actualDepartements = departementRestController.getDepartements();
        assertEquals(expectedDepartements, actualDepartements);
        verify(departementService, times(1)).retrieveAllDepartements();
    }

    @Test
    void testRetrieveDepartement() {
        Integer departementId = 1;
        Departement expectedDepartement = new Departement();
        expectedDepartement.setIdDepart(departementId);
        when(departementService.retrieveDepartement(departementId)).thenReturn(expectedDepartement);
        Departement actualDepartement = departementRestController.retrieveDepartement(departementId);
        assertEquals(expectedDepartement, actualDepartement);
        verify(departementService, times(1)).retrieveDepartement(departementId);
    }

    @Test
    void testRemoveDepartement() {
        Integer departementId = 1;
        doNothing().when(departementService).deleteDepartement(departementId);
        departementRestController.removeDepartement(departementId);
        verify(departementService, times(1)).deleteDepartement(departementId);
    }

    @Test
    void testAddDepartement() {
        // Création de l'objet DepartementDTO pour le test
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setNomDep("Informatique");


        // Création de l'objet Departement attendu
        Departement expectedDepartement = new Departement();
        expectedDepartement.setNomDepart("Informatique");


        // Simulation du comportement du service
        when(departementService.addDepartement(any(Departement.class))).thenReturn(expectedDepartement);

        // Appel de la méthode et vérification
        Departement result = departementRestController.addDepartement(departementDTO);
        assertEquals(expectedDepartement, result);
    }
}
