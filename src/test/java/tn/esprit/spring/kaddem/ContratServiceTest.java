package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratServiceTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testRetrieveAllContrats_ReturnsListOfContrats() {
        List<Contrat> contrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(contrats);

        List<Contrat> result = contratService.retrieveAllContrats();
        assertEquals(2, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testAddContrat_SavesAndReturnsContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);
        assertNotNull(result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testRetrieveContrat_ReturnsContrat_WhenExists() {
        Contrat contrat = new Contrat();
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(1);
        assertNotNull(result);
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveContrat_ReturnsNull_WhenNotFound() {
        when(contratRepository.findById(1)).thenReturn(Optional.empty());

        Contrat result = contratService.retrieveContrat(1);
        assertNull(result);
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    void testRemoveContrat_DeletesContrat_WhenExists() {
        Contrat contrat = new Contrat();
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        doNothing().when(contratRepository).delete(contrat);

        contratService.removeContrat(1);
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void testAffectContratToEtudiant_AssignsContratToEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());
        Contrat contrat = new Contrat();
        contrat.setArchive(false);

        when(etudiantRepository.findByNomEAndPrenomE("John", "Doe")).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(1, "John", "Doe");
        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testNbContratsValides_ReturnsValidContratCount() {
        when(contratRepository.getnbContratsValides(any(Date.class), any(Date.class))).thenReturn(5);

        int result = contratService.nbContratsValides(new Date(), new Date());
        assertEquals(5, result);
        verify(contratRepository, times(1)).getnbContratsValides(any(Date.class), any(Date.class));
    }

    @Test
    void testGetChiffreAffaireEntreDeuxDates_ReturnsRevenue() {
        Contrat contrat1 = new Contrat();
        contrat1.setSpecialite(Specialite.IA);
        Contrat contrat2 = new Contrat();
        contrat2.setSpecialite(Specialite.CLOUD);

        List<Contrat> contrats = Arrays.asList(contrat1, contrat2);
        when(contratRepository.findAll()).thenReturn(contrats);

        float result = contratService.getChiffreAffaireEntreDeuxDates(new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
        assertTrue(result > 0);
        verify(contratRepository, times(1)).findAll();
    }
}
