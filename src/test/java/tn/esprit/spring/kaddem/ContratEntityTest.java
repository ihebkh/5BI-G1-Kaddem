package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContratEntityTest {

    @Test
    void testGettersAndSetters() {
        // Create instances for test
        Contrat contrat = new Contrat();
        Date startDate = new Date();
        Date endDate = new Date();
        Specialite specialite = Specialite.IA;
        Boolean archive = true;
        Integer montant = 1000;

        // Set values
        contrat.setDateDebutContrat(startDate);
        contrat.setDateFinContrat(endDate);
        contrat.setSpecialite(specialite);
        contrat.setArchive(archive);
        contrat.setMontantContrat(montant);

        // Validate values with getters
        assertEquals(startDate, contrat.getDateDebutContrat());
        assertEquals(endDate, contrat.getDateFinContrat());
        assertEquals(specialite, contrat.getSpecialite());
        assertEquals(archive, contrat.getArchive());
        assertEquals(montant, contrat.getMontantContrat());
    }

    @Test
    void testRelationshipWithEtudiant() {
        // Create instances for test
        Contrat contrat = new Contrat();
        Etudiant etudiant = new Etudiant();

        // Set relationship
        contrat.setEtudiant(etudiant);

        // Validate relationship
        assertEquals(etudiant, contrat.getEtudiant());
    }

    @Test
    void testToStringMethod() {
        // Prepare sample data
        Date startDate = new Date();
        Date endDate = new Date();
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setDateDebutContrat(startDate);
        contrat.setDateFinContrat(endDate);
        contrat.setSpecialite(Specialite.IA);
        contrat.setArchive(true);
        contrat.setMontantContrat(1500);

        // Validate toString output
        String contratString = contrat.toString();
        assertTrue(contratString.contains("idContrat=1"));
        assertTrue(contratString.contains("dateDebutContrat=" + startDate));
        assertTrue(contratString.contains("dateFinContrat=" + endDate));
        assertTrue(contratString.contains("specialite=IA"));
        assertTrue(contratString.contains("archive=true"));
        assertTrue(contratString.contains("montantContrat=1500"));
    }
}
