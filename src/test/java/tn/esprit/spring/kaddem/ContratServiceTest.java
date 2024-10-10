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
    void testAddContrat_DoesNotSaveNullContrat() {
        Contrat result = contratService.addContrat(null);
        assertNull(result);
        verify(contratRepository, never()).save(any(Contrat.class));
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
    void testRemoveContrat_DoesNothing_WhenContratNotFound() {
        when(contratRepository.findById(1)).thenReturn(Optional.empty());

        contratService.removeContrat(1);
        verify(contratRepository, never()).delete(any(Contrat.class));
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
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // 30 days later
        float expectedRevenue = 1500f; // Adjust based on your expectations for revenue calculation

        // Mock the repository to return specific contracts
        Contrat contrat1 = new Contrat();
        contrat1.setSpecialite(Specialite.IA);
        contrat1.setDateDebutContrat(startDate);
        contrat1.setDateFinContrat(endDate);

        Contrat contrat2 = new Contrat();
        contrat2.setSpecialite(Specialite.CLOUD);
        contrat2.setDateDebutContrat(startDate);
        contrat2.setDateFinContrat(endDate);

        when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat1, contrat2));

        // Call the service method
        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        // Assertions
        assertEquals(expectedRevenue, result, 0.001); // Adjust the expectedRevenue based on actual calculation
        verify(contratRepository, times(1)).findAll();
    }


    @Test
    void testUpdateContrat_UpdatesAndReturnsContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        Contrat updatedContrat = contratService.updateContrat(contrat);
        assertNotNull(updatedContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testLombokGeneratedMethods() {
        Contrat contrat = new Contrat();

        // Testing setters
        contrat.setIdContrat(1);
        contrat.setDateDebutContrat(new Date());
        contrat.setDateFinContrat(new Date());
        contrat.setSpecialite(Specialite.IA);
        contrat.setArchive(true);
        contrat.setMontantContrat(500);

        // Testing getters
        assertEquals(1, contrat.getIdContrat());
        assertNotNull(contrat.getDateDebutContrat());
        assertNotNull(contrat.getDateFinContrat());
        assertEquals(Specialite.IA, contrat.getSpecialite());
        assertTrue(contrat.getArchive());
        assertEquals(500, contrat.getMontantContrat());

        // Testing toString (optional, based on your preference)
        assertNotNull(contrat.toString());
    }

    @Test
    void testToStringMethod() {
        // Create a sample date
        Date startDate = new Date();
        Date endDate = new Date();

        // Create a Contrat instance with specific values
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setDateDebutContrat(startDate);
        contrat.setDateFinContrat(endDate);
        contrat.setSpecialite(Specialite.IA);
        contrat.setArchive(true);
        contrat.setMontantContrat(1500);

        // Call toString and check if it contains expected values
        String contratString = contrat.toString();

        // Assertions - checking if certain properties are included in the toString output
        assertTrue(contratString.contains("idContrat=1"));
        assertTrue(contratString.contains("dateDebutContrat=" + startDate));
        assertTrue(contratString.contains("dateFinContrat=" + endDate));
        assertTrue(contratString.contains("specialite=IA"));
        assertTrue(contratString.contains("archive=true"));
        assertTrue(contratString.contains("montantContrat=1500"));
    }

    @Test
    void testContratConstructor() {
        Date startDate = new Date();
        Date endDate = new Date();
        Specialite specialite = Specialite.CLOUD;
        Boolean archive = false;
        Integer montant = 2000;

        Contrat contrat = new Contrat(startDate, endDate, specialite, archive, montant);

        assertEquals(startDate, contrat.getDateDebutContrat());
        assertEquals(endDate, contrat.getDateFinContrat());
        assertEquals(specialite, contrat.getSpecialite());
        assertEquals(archive, contrat.getArchive());
        assertEquals(montant, contrat.getMontantContrat());
    }

    @Test
    void testRelationshipWithEtudiant() {
        Contrat contrat = new Contrat();
        Etudiant etudiant = new Etudiant();

        contrat.setEtudiant(etudiant);

        assertEquals(etudiant, contrat.getEtudiant());
    }
}
