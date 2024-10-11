package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceUnitTest {

    @Mock
    ContratRepository contratRepository;

    @InjectMocks
    ContratServiceImpl contratServiceImpl;

    Contrat contrat1, contrat2;

    @BeforeEach
    public void setup() {
        contrat1 = new Contrat(1, new Date(), new Date(), Specialite.IA);
        contrat2 = new Contrat(2, new Date(), new Date(), Specialite.RESEAUX);
    }



    @Test
    void testAddContrat() {
        when(contratRepository.save(contrat1)).thenReturn(contrat1);
        Contrat savedContrat = contratServiceImpl.addContrat(contrat1);

        assertNotNull(savedContrat);
        assertEquals(contrat1.getSpecialite(), savedContrat.getSpecialite());
        verify(contratRepository, times(1)).save(contrat1);
    }



    @Test
    void testUpdateContrat() {
        contrat1.setSpecialite(Specialite.RESEAUX);
        when(contratRepository.save(contrat1)).thenReturn(contrat1);
        Contrat updatedContrat = contratServiceImpl.updateContrat(contrat1);

        assertEquals(Specialite.RESEAUX, updatedContrat.getSpecialite());
        verify(contratRepository, times(1)).save(contrat1);
    }

    @Test
    void testRemoveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat1));
        doNothing().when(contratRepository).delete(contrat1);

        contratServiceImpl.removeContrat(1);

        verify(contratRepository, times(1)).delete(contrat1);
    }

    @Test
    void testRetrieveInvalidContrat() {
        when(contratRepository.findById(3)).thenReturn(Optional.empty());

        Contrat invalidContrat = contratServiceImpl.retrieveContrat(3);

        assertNull(invalidContrat);
    }
}
