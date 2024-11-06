package tn.esprit.spring.kaddem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

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
    void testRemoveUniversite() {
        Integer idUniversite = 1;
        Universite universite = new Universite(idUniversite, "University E");
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));

        universiteService.removeUniversite(idUniversite);

        verify(universiteRepository, times(1)).delete(universite);
    }
}
