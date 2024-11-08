package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EtudiantEntityTest {
    @Test
    void testGettersAndSetters() {
        // Create an instance of Etudiant for test
        Etudiant etudiant = new Etudiant();
        String nomE = "YAS";

        // Set values
        etudiant.setNomE(nomE);

        // Validate values with getters
        assertEquals(nomE, etudiant.getNomE());
    }

    @Test
    void testEtudiantConstructor() {
        // Arrange
        String expectedNomE = "ETUDIANT Yas";

        // Act
        Etudiant etudiant = new Etudiant(expectedNomE);

        // Assert
        assertEquals(expectedNomE, etudiant.getNomE(), "The nomE should be correctly assigned.");
    }

   /* @Test
    void testRelationshipWithDepartement() {
        // Create instances for test
        Etudiant etudiant = new Etudiant();
        Set<Departement> departements = new HashSet<>();

        // Set relationship
        etudiant.setDepartement((Departement) departements);

        // Validate relationship
        assertEquals(departements, etudiant.getDepartement());
    }*/

   /* @Test
    void testParameterizedConstructor() {
        Integer idE = 1;
        String nomE = "YAS";

        Etudiant etudiant = new Etudiant(idE, nomE);

        assertEquals(idE, etudiant.getIdEtudiant());
        assertEquals(nomE, etudiant.getNomE());
    }*/

    /*@Test
    void testAddDepartement() {
        // Create Etudiant and Departement instances
        Etudiant etudiant = new Etudiant("YAS");
        Departement departement = new Departement("Computer Science");

        // Ensure departements is initialized
        etudiant.setDepartement(new HashSet<>());
        etudiant.getDepartement().add(departement);

        // Assert that the departement was added
        assertTrue(etudiant.getDepartement().contains(departement), "Departement should be added to Etudiant");
    }*/
}
