package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    // Test pour récupérer tous les étudiants
    @Test
    void testRetrieveAllEtudiants() {
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> expectedEtudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.findAll()).thenReturn(expectedEtudiants);

        List<Etudiant> actualEtudiants = etudiantService.retrieveAllEtudiants();

        assertEquals(expectedEtudiants, actualEtudiants);
        verify(etudiantRepository, times(1)).findAll();
    }

    // Test pour ajouter un étudiant
    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();

        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        assertEquals(etudiant, savedEtudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    // Test pour mettre à jour un étudiant
    @Test
    void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant();

        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);

        assertEquals(etudiant, updatedEtudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    // Test pour récupérer un étudiant par son ID
    @Test
    void testRetrieveEtudiant() {
        Integer etudiantId = 1;
        Etudiant etudiant = new Etudiant();

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        Etudiant actualEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        assertEquals(etudiant, actualEtudiant);
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    // Test pour récupérer un étudiant qui n'existe pas
    @Test
    void testRetrieveEtudiantNotFound() {
        Integer etudiantId = 1;

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

        Etudiant actualEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        assertNull(actualEtudiant);
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    // Test pour supprimer un étudiant
    @Test
    void testRemoveEtudiant() {
        Integer etudiantId = 1;
        Etudiant etudiant = new Etudiant();

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    // Test pour supprimer un étudiant qui n'existe pas
    @Test
    void testRemoveEtudiantNotFound() {
        Integer etudiantId = 1;

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(0)).delete(any(Etudiant.class));
    }

    // Test pour assigner un étudiant à un département
    @Test
    void testAssignEtudiantToDepartement() {
        Integer etudiantId = 1;
        Integer departementId = 1;

        Etudiant etudiant = new Etudiant();
        Departement departement = new Departement();

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        etudiantService.assignEtudiantToDepartement(etudiantId, departementId);

        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).save(etudiant);
    }



    // Test pour récupérer les étudiants par département
    @Test
    void testGetEtudiantsByDepartement() {
        Integer departementId = 1;
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> expectedEtudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(departementId)).thenReturn(expectedEtudiants);

        List<Etudiant> actualEtudiants = etudiantService.getEtudiantsByDepartement(departementId);

        assertEquals(expectedEtudiants, actualEtudiants);
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(departementId);
    }
}
