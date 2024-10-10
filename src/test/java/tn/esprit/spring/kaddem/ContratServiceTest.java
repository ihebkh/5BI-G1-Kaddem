package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    private Contrat contrat;
    private Etudiant etudiant;

    @BeforeEach
    public void setUp() {
        contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setArchive(false);
        contrat.setDateFinContrat(new Date());

        etudiant = new Etudiant();
        etudiant.setNomE("NomTest");
        etudiant.setPrenomE("PrenomTest");
        etudiant.setContrats(new HashSet<>());
    }



    @Test
    public void testAddContrat() {
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat savedContrat = contratService.addContrat(contrat);
        assertNotNull(savedContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    public void testRetrieveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        Contrat retrievedContrat = contratService.retrieveContrat(1);
        assertNotNull(retrievedContrat);
        assertEquals(1, retrievedContrat.getIdContrat());
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    public void testRemoveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        doNothing().when(contratRepository).delete(contrat);

        contratService.removeContrat(1);
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    public void testAffectContratToEtudiant() {
        contrat.setArchive(false);
        Set<Contrat> contrats = new HashSet<>();
        contrats.add(contrat);
        etudiant.setContrats(contrats);

        when(etudiantRepository.findByNomEAndPrenomE("NomTest", "PrenomTest")).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        Contrat affectedContrat = contratService.affectContratToEtudiant(1, "NomTest", "PrenomTest");

        assertNotNull(affectedContrat);
        assertEquals(etudiant, affectedContrat.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    public void testNbContratsValides() {
        Date startDate = new Date();
        Date endDate = new Date();
        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(3);

        Integer nbContrats = contratService.nbContratsValides(startDate, endDate);
        assertEquals(3, nbContrats);
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }

    @Test
    public void testRetrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = new ArrayList<>();
        contrats.add(contrat);

        when(contratRepository.findAll()).thenReturn(contrats);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        contratService.retrieveAndUpdateStatusContrat();

        verify(contratRepository, times(1)).findAll();
        verify(contratRepository, atLeastOnce()).save(contrat);
    }


}
