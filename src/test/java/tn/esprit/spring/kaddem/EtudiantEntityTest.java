package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.*;

import java.util.ArrayList;
import java.util.HashSet;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EtudiantEntityTest {
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

    @Test
    void testRelationshipWithDepartement() {
        // Create instances for the test
        Departement departement = new Departement("Computer Science");
        Etudiant etudiant = new Etudiant();

        // Set the relationship
        etudiant.setDepartement(departement);

        // Validate the relationship
        assertEquals(departement, etudiant.getDepartement(), "Departement should be properly set in Etudiant");
    }

    @Test
    void testParameterizedConstructor() {
        Integer idE = 1;
        String nomE = "YAS";
        String prenomE = "Doe";

        // Use parameterized constructor
        Etudiant etudiant = new Etudiant(idE, nomE, prenomE, Option.GAMIX);

        assertEquals(idE, etudiant.getIdEtudiant());
        assertEquals(nomE, etudiant.getNomE());
        assertEquals(prenomE, etudiant.getPrenomE());
        assertEquals(Option.GAMIX, etudiant.getOp(), "Option should be properly set");
    }

    @Test
    void testAddContratToEtudiant() {
        // Create Etudiant and Contrat instances
        Etudiant etudiant = new Etudiant("John", "Doe");
        Contrat contrat = new Contrat();

        // Ensure the contracts list is initialized
        etudiant.setContrats(new HashSet<>());
        etudiant.getContrats().add(contrat);

        // Assert that the contract was added
        assertTrue(etudiant.getContrats().contains(contrat), "Contrat should be added to Etudiant");
    }

    @Test
    void testAddEquipeToEtudiant() {
        // Create Etudiant and Equipe instances
        Etudiant etudiant = new Etudiant("John", "Doe");
        Equipe equipe = new Equipe("Development Team");

        // Ensure the equipes list is initialized
        etudiant.setEquipes(new ArrayList<>());
        etudiant.getEquipes().add(equipe);

        // Assert that the team was added
        assertTrue(etudiant.getEquipes().contains(equipe), "Equipe should be added to Etudiant");
    }
}
