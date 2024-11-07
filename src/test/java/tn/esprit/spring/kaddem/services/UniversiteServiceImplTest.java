package tn.esprit.spring.kaddem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplTest {

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> universites = Collections.singletonList(new Universite(1, "University A"));
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertEquals(1, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        Universite universite = new Universite("University B");
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertNotNull(result);
        assertEquals("University B", result.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testUpdateUniversite() {
        Universite universite = new Universite(1, "University C");
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.updateUniversite(universite);

        assertNotNull(result);
        assertEquals("University C", result.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        Integer idUniversite = 1;
        Universite universite = new Universite(idUniversite, "University D");
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(idUniversite);

        assertNotNull(result);
        assertEquals("University D", result.getNomUniv());
        verify(universiteRepository, times(1)).findById(idUniversite);
    }

    @Test
    void testRetrieveNonExistentUniversite() {
        Integer idUniversite = 999;
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.empty());

        Universite result = universiteService.retrieveUniversite(idUniversite);

        assertNull(result, "Expected null when retrieving a non-existent university.");
        verify(universiteRepository, times(1)).findById(idUniversite);
    }

    @Test
    void testRemoveUniversite() {
        Universite universite = new Universite();
        universite.setIdUniv(1);

        doNothing().when(universiteRepository).deleteById(1);

        universiteService.removeUniversite(1);

        verify(universiteRepository, times(1)).deleteById(1);
    }

    @Test
    void testRemoveNonExistentUniversite() {
        doThrow(new RuntimeException("Not found")).when(universiteRepository).deleteById(999);

        assertThrows(RuntimeException.class, () -> universiteService.removeUniversite(999),
                "Expected a RuntimeException when deleting a non-existent university.");

        verify(universiteRepository, times(1)).deleteById(999);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        // Arrange
        Universite universite = new Universite();
        universite.setIdUniv(1);
        universite.setDepartements(new HashSet<>()); // Make sure it's initialized in the test
        Departement departement = new Departement();
        departement.setIdDepart(1);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        universiteService.assignUniversiteToDepartement(1, 1);

        // Assert
        verify(universiteRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).save(any(Universite.class));  // Ensure save was called
    }


}
