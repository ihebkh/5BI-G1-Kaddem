package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.ContratDTO;
import static org.junit.jupiter.api.Assertions.*;

public class ContratDTOTest {
    @Test
    public void testGettersAndSetters() {
        ContratDTO contratDTO = new ContratDTO();

        String dateDebut = "2024-10-26";
        String dateFin = "2025-10-26";
        String specialite = "RESEAUX";
        Boolean archive = false;
        Integer montant = 1500;


        // Tester les setters
        contratDTO.setDateDebutContrat(dateDebut);
        contratDTO.setDateFinContrat(dateFin);
        contratDTO.setSpecialite(specialite);
        contratDTO.setArchive(archive);
        contratDTO.setMontantContrat(montant);

        assertEquals(dateDebut, contratDTO.getDateDebutContrat());
        assertEquals(dateFin, contratDTO.getDateFinContrat());
        assertEquals(specialite, contratDTO.getSpecialite());
        assertEquals(archive, contratDTO.getArchive());
        assertEquals(montant, contratDTO.getMontantContrat());
    }
}
