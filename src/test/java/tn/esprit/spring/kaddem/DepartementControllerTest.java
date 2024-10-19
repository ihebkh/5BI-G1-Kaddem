package tn.esprit.spring.kaddem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.controllers.DepartementRestController;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.services.IDepartementService;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DepartementControllerTest {

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
}
