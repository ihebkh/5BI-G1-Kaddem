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
import java.util.Optional;

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

    @Test
    void testRetrieveEtudiant() {
        // ID du etudiant à récupérer
        Integer etudiantId = 1;
        Etudiant expectedEtudiant = new Etudiant();

        // Simulation du comportement du repository
        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(expectedEtudiant));

        // Appel de la méthode de service
        Etudiant actualEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        // Vérification du résultat
        assertEquals(expectedEtudiant, actualEtudiant);
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }


    @Test
    void testRemoveEtudiant() {
        // ID du etudiant à supprimer
        Integer etudiantId = 1;
        Etudiant etudiant = new Etudiant();

        // Simulation du comportement du repository
        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        // Appel de la méthode de suppression
        etudiantService.removeEtudiant(etudiantId);

        // Vérification que la méthode delete a été appelée
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    
}