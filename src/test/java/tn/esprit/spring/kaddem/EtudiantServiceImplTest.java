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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    // Test retrieving all Etudiants
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

    // Test retrieving an Etudiant by ID
    @Test
    void testRetrieveEtudiant() {
        Integer etudiantId = 1;
        Etudiant expectedEtudiant = new Etudiant();

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(expectedEtudiant));

        Etudiant actualEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        assertEquals(expectedEtudiant, actualEtudiant);
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    // Test retrieving an Etudiant that is not found
    @Test
    void testRetrieveEtudiantNotFound() {
        Integer etudiantId = 1;

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

        Etudiant actualEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        assertNull(actualEtudiant);
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    // Test removing an Etudiant
    @Test
    void testRemoveEtudiant() {
        Integer etudiantId = 1;
        Etudiant etudiant = new Etudiant();

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    // Test removing an Etudiant that is not found
    @Test
    void testRemoveEtudiantNotFound() {
        Integer etudiantId = 1;

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(0)).delete(any(Etudiant.class));
    }

    // Test adding a new Etudiant
    @Test
    void testAddEtudiant() {
        Etudiant newEtudiant = new Etudiant();
        when(etudiantRepository.save(newEtudiant)).thenReturn(newEtudiant);

        Etudiant addedEtudiant = etudiantService.addEtudiant(newEtudiant);

        assertEquals(newEtudiant, addedEtudiant);
        verify(etudiantRepository, times(1)).save(newEtudiant);
    }

    // Test updating an existing Etudiant
    @Test
    void testUpdateEtudiant() {
        Etudiant existingEtudiant = new Etudiant();

        when(etudiantRepository.save(existingEtudiant)).thenReturn(existingEtudiant);

        Etudiant updatedEtudiant = etudiantService.updateEtudiant(existingEtudiant);

        assertEquals(existingEtudiant, updatedEtudiant);
        verify(etudiantRepository, times(1)).save(existingEtudiant);
    }

    // Test updating an Etudiant that doesn't exist
    @Test
    void testUpdateEtudiantNotFound() {
        Etudiant updatedEtudiant = new Etudiant();


        Etudiant result = etudiantService.updateEtudiant(updatedEtudiant);

        assertNull(result);
        verify(etudiantRepository, times(0)).save(any(Etudiant.class));
    }

    // Test adding a null Etudiant
    @Test
    void testAddNullEtudiant() {
        Etudiant result = etudiantService.addEtudiant(null);

        assertNull(result);
        verify(etudiantRepository, times(0)).save(any(Etudiant.class));
    }
}
