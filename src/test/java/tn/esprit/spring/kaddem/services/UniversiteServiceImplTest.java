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
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplTest {

    @InjectMocks
    private UniversiteServiceImpl universiteService;

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
        Integer universiteId = 1;
        Integer departementId = 1;
        Universite universite = new Universite(universiteId, "University E");
        Departement departement = new Departement(departementId, "Department E");

        when(universiteRepository.findById(universiteId)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(universite)).thenReturn(universite);

        universiteService.assignUniversiteToDepartement(universiteId, departementId);

        verify(universiteRepository, times(1)).findById(universiteId);
        verify(universiteRepository, times(1)).save(universite);
    }
}
