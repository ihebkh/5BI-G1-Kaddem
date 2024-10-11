package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @Mock
    ContratRepository contratRepository;

    @InjectMocks
    ContratServiceImpl contratService;

    @Test
    void testRetrieveAllContrats() {
        // Préparation des données de test
        Contrat contrat1 = new Contrat();
        Contrat contrat2 = new Contrat();
        List<Contrat> expectedContrats = Arrays.asList(contrat1, contrat2);

        // Simulation du comportement du repository
        when(contratRepository.findAll()).thenReturn(expectedContrats);

        // Appel de la méthode de service
        List<Contrat> actualContrats = contratService.retrieveAllContrats();

        // Vérification du résultat
        assertEquals(expectedContrats, actualContrats);
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveContrat() {
        // ID du contrat à récupérer
        Integer contratId = 1;
        Contrat expectedContrat = new Contrat();

        // Simulation du comportement du repository
        when(contratRepository.findById(contratId)).thenReturn(Optional.of(expectedContrat));

        // Appel de la méthode de service
        Contrat actualContrat = contratService.retrieveContrat(contratId);

        // Vérification du résultat
        assertEquals(expectedContrat, actualContrat);
        verify(contratRepository, times(1)).findById(contratId);
    }

    @Test
    void testRetrieveContrat_NotFound() {
        // ID du contrat inexistant
        Integer contratId = 2;

        // Simulation du comportement du repository
        when(contratRepository.findById(contratId)).thenReturn(Optional.empty());

        // Appel de la méthode de service
        Contrat actualContrat = contratService.retrieveContrat(contratId);

        // Vérification que le résultat est null
        assertNull(actualContrat);
        verify(contratRepository, times(1)).findById(contratId);
    }

    @Test
    void testRemoveContrat() {
        // ID du contrat à supprimer
        Integer contratId = 1;
        Contrat contrat = new Contrat();

        // Simulation du comportement du repository
        when(contratRepository.findById(contratId)).thenReturn(Optional.of(contrat));

        // Appel de la méthode de suppression
        contratService.removeContrat(contratId);

        // Vérification que la méthode delete a été appelée
        verify(contratRepository, times(1)).delete(contrat);
    }
}