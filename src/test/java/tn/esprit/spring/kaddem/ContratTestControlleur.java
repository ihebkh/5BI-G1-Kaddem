package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Contrat;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ContratTestControlleur {
    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;


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

    @Test
    void testAffectContratToEtudiant_NoExistingContracts() {
        Integer idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";
        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat();

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testAffectContratToEtudiant_ActiveContractsUnderLimit() {
        Integer idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";

        Contrat activeContract1 = new Contrat();
        activeContract1.setArchive(true);
        Contrat activeContract2 = new Contrat();
        activeContract2.setArchive(true);

        Set<Contrat> existingContracts = new HashSet<>();
        existingContracts.add(activeContract1);
        existingContracts.add(activeContract2);

        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(existingContracts);

        Contrat contrat = new Contrat();

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testAffectContratToEtudiant_ActiveContractsExceedsLimit() {
        Integer idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";

        Contrat activeContract1 = new Contrat();
        activeContract1.setArchive(true);
        Contrat activeContract2 = new Contrat();
        activeContract2.setArchive(true);
        Contrat activeContract3 = new Contrat();
        activeContract3.setArchive(true);
        Contrat activeContract4 = new Contrat();
        activeContract4.setArchive(true);
        Contrat activeContract5 = new Contrat();
        activeContract5.setArchive(true);

        Set<Contrat> existingContracts = new HashSet<>();
        existingContracts.add(activeContract1);
        existingContracts.add(activeContract2);
        existingContracts.add(activeContract3);
        existingContracts.add(activeContract4);
        existingContracts.add(activeContract5);

        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(existingContracts);

        Contrat contrat = new Contrat();

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertNull(result.getEtudiant()); // Contract should not be assigned due to exceeded limit
        verify(contratRepository, never()).save(contrat);
    }

    @Test
    void testAffectContratToEtudiant_NullArchiveField() {
        Integer idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";

        Contrat contractWithNullArchive = new Contrat();
        contractWithNullArchive.setArchive(null);

        Set<Contrat> existingContracts = new HashSet<>();
        existingContracts.add(contractWithNullArchive);

        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(existingContracts);

        Contrat contrat = new Contrat();

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertEquals(etudiant, result.getEtudiant()); // Contract should be assigned
        verify(contratRepository, times(1)).save(contrat);
    }




}
