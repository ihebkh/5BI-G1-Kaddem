package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {
    @Mock
    private EtudiantRepository etudiantRepository;


    @InjectMocks
    private EtudiantServiceImpl etudiantService;
    
    
    @Test
    void testRetrieveAllEtudiants() {
        // Préparation des données de test
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> expectedEtudiants = Arrays.asList(etudiant1, etudiant2);

        // Simulation du comportement du repository
        when(etudiantRepository.findAll()).thenReturn(expectedEtudiants);

        // Appel de la méthode de service
        List<Etudiant> actualEtudiants = etudiantService.retrieveAllEtudiants();

        // Vérification du résultat
        assertEquals(expectedEtudiants, actualEtudiants);
        verify(etudiantRepository, times(1)).findAll();
    }
}
