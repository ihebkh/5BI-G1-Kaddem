package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ContratControllerTest {

    @Mock
    ContratServiceImpl contratService;
    @Mock
    ContratRepository contratRepository;

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

    @Test
    void testRetrieveContrat() {
        // ID du contrat à récupérer
        Integer contratId = 1;
        Contrat expectedContrat = new Contrat();

        // Simulation du comportement du service
        when(contratService.retrieveContrat(contratId)).thenReturn(expectedContrat);

        // Appel de la méthode du contrôleur
        Contrat actualContrat = contratController.retrieveContrat(contratId);

        // Vérification du résultat
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).retrieveContrat(contratId);
    }

    @Test
    void testRetrieveContrat_NotFound() {
        // ID du contrat inexistant
        Integer contratId = 2;

        // Simulation du comportement du service
        when(contratService.retrieveContrat(contratId)).thenReturn(null);

        // Appel de la méthode du contrôleur
        Contrat actualContrat = contratController.retrieveContrat(contratId);

        // Vérification que le contrat retourné est null pour un ID inexistant
        assertNull(actualContrat);
        verify(contratService, times(1)).retrieveContrat(contratId);
    }

    @Test
    void testAssignContratToEtudiant() {
        // Variables for the test
        Integer contratId = 1;
        String nomE = "Doe";
        String prenomE = "John";
        Contrat expectedContrat = new Contrat();
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE(nomE);
        etudiant.setPrenomE(prenomE);
        expectedContrat.setEtudiant(etudiant);

        // Simulate the service behavior
        when(contratService.affectContratToEtudiant(contratId, nomE, prenomE)).thenReturn(expectedContrat);

        // Call the method in the controller
        Contrat actualContrat = contratController.assignContratToEtudiant(contratId, nomE, prenomE);

        // Assert that the returned contract matches the expected contract
        assertEquals(expectedContrat, actualContrat);
        verify(contratService, times(1)).affectContratToEtudiant(contratId, nomE, prenomE);
    }

    @Test
    void testMajStatusContrat() {
        // Call the majStatusContrat method in the controller
        contratController.majStatusContrat();

        // Verify that the retrieveAndUpdateStatusContrat method is called exactly once
        verify(contratService, times(1)).retrieveAndUpdateStatusContrat();
    }


}