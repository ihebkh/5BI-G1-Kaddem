package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContratEntityTest {

    private Contrat contrat;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    void setUp() {
        startDate = new Date();
        endDate = new Date();
        contrat = new Contrat(1, startDate, endDate, Specialite.IA, false, 1000);
    }

    @Test
    void testConstructorAndGetters() {
        // Validate that the constructor initializes the fields correctly
        assertEquals(1, contrat.getIdContrat());
        assertEquals(startDate, contrat.getDateDebutContrat());
        assertEquals(endDate, contrat.getDateFinContrat());
        assertEquals(Specialite.IA, contrat.getSpecialite());
        assertFalse(contrat.getArchive());
        assertEquals(1000, contrat.getMontantContrat());
    }

    @Test
    void testSetters() {
        // Create new values for testing the setters
        Date newStartDate = new Date();
        Date newEndDate = new Date();
        contrat.setIdContrat(2);
        contrat.setDateDebutContrat(newStartDate);
        contrat.setDateFinContrat(newEndDate);
        contrat.setSpecialite(Specialite.IA);
        contrat.setArchive(true);
        contrat.setMontantContrat(2000);

        // Verify that the setters update the fields correctly
        assertEquals(2, contrat.getIdContrat());
        assertEquals(newStartDate, contrat.getDateDebutContrat());
        assertEquals(newEndDate, contrat.getDateFinContrat());
        assertEquals(Specialite.IA, contrat.getSpecialite());
        assertTrue(contrat.getArchive());
        assertEquals(2000, contrat.getMontantContrat());
    }

    @Test
    void testToString() {
        String toStringOutput = contrat.toString();

        // Verify that the toString output contains the expected field values
        assertTrue(toStringOutput.contains("idContrat=1"));
        assertTrue(toStringOutput.contains("dateDebutContrat=" + startDate));
        assertTrue(toStringOutput.contains("dateFinContrat=" + endDate));
        assertTrue(toStringOutput.contains("specialite=INFORMATIQUE"));
        assertTrue(toStringOutput.contains("archive=false"));
        assertTrue(toStringOutput.contains("montantContrat=1000"));
    }
}
