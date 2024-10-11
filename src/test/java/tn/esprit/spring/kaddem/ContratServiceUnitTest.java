package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContratControllerTest {

    @Mock
    ContratServiceImpl contratService;

    @InjectMocks
    ContratRestController contratController;

    @Test
    void testRemoveContrat() {
        // ID du contrat à supprimer
        Integer contratId = 1;

        // Appel de la méthode de suppression du contrôleur
        contratController.removeContrat(contratId);

        // Vérification que la méthode removeContrat du service a été appelée avec le bon ID
        verify(contratService, times(1)).removeContrat(contratId);
    }

    @Test
    void testGetContrats() {
        // Préparation des données de test
        Contrat contrat1 = new Contrat();
        Contrat contrat2 = new Contrat();
        List<Contrat> expectedContrats = Arrays.asList(contrat1, contrat2);

        // Simulation du comportement du service
        when(contratService.retrieveAllContrats()).thenReturn(expectedContrats);

        // Appel de la méthode du contrôleur
        List<Contrat> actualContrats = contratController.getContrats();

        // Vérification du résultat
        assertEquals(expectedContrats, actualContrats);
        verify(contratService, times(1)).retrieveAllContrats();
    }
}
