package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

 class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetChiffreAffaireEntreDeuxDates() {
        // Arrange
        Date startDate = new Date(2024, 1, 1); // Example start date
        Date endDate = new Date(2024, 3, 1); // Example end date (two months later)

        List<Contrat> contrats = Arrays.asList(
                new Contrat(Specialite.IA),       // Expecting 2 * 300
                new Contrat(Specialite.CLOUD),    // Expecting 2 * 400
                new Contrat(Specialite.RESEAUX),  // Expecting 2 * 350
                new Contrat(Specialite.SECURITE)  // Expecting 2 * 450
        );

        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        // Assert
        float expectedChiffreAffaire = (2 * 300) + (2 * 400) + (2 * 350) + (2 * 450);
        assertEquals(expectedChiffreAffaire, result, "The chiffre d'affaire calculated is incorrect.");
    }
}
