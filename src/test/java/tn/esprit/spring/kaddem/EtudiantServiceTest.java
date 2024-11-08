package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class EtudiantServiceTest {
    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Test
    void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = Collections.singletonList(new Etudiant("Etudiant: Nom: A", "Prenom : B"));
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertEquals(1, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }
    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant("Etudiant: Nom: A");
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);

        assertNotNull(result);
        assertEquals("Etudiant: Nom: A", result.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant);
    }
    @Test
    void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant(1, "Etudiant A");
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant)); // Stub findById

        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.updateEtudiant(etudiant);

        assertNotNull(result);
        assertEquals("Etudiant A", result.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRetrieveEtudiant() {
        Integer idEtudiant = 1;
        Etudiant etudiant = new Etudiant(idEtudiant, "Etudiant A");
        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(idEtudiant);

        assertNotNull(result);
        assertEquals("Etudiant A", result.getNomE());
        verify(etudiantRepository, times(1)).findById(idEtudiant);
    }

    @Test
    void testRetrieveNonExistentEtudiant() {
        Integer idEtudiant = 999;
        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.empty());

        Etudiant result = etudiantService.retrieveEtudiant(idEtudiant);

        assertNull(result, "Expected null when retrieving a non-existent student.");
        verify(etudiantRepository, times(1)).findById(idEtudiant);
    }

    @Test
    void testRemoveEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);

        doNothing().when(etudiantRepository).deleteById(1);

        etudiantService.removeEtudiant(1);

        verify(etudiantRepository, times(1)).deleteById(1);
    }

    @Test
    void testRemoveNonExistentEtudiant() {
        Integer idEtudiant = 999;

        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> etudiantService.removeEtudiant(idEtudiant),
                "Expected a RuntimeException when deleting a non-existent student.");

        verify(etudiantRepository, times(1)).findById(idEtudiant);
        verify(etudiantRepository, never()).deleteById(idEtudiant);  // deleteById should never be called
    }

   /* @Test
    void testAssignEtudiantToDepartement() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setDepartement(new HashSet<>()); // Make sure it's initialized in the test
        Departement departement = new Departement();
        departement.setIdDepart(1);

        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        etudiantService.assignEtudiantToDepartement(1, 1);

        // Assert
        verify(etudiantRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));  // Ensure save was called
    }*/



}
