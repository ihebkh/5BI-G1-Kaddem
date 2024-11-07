package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UniversiteEntityTest {

    @Test
    void testGettersAndSetters() {
        // Create an instance of Universite for test
        Universite universite = new Universite();
        String nomUniv = "ESPRIT";

        // Set values
        universite.setNomUniv(nomUniv);

        // Validate values with getters
        assertEquals(nomUniv, universite.getNomUniv());
    }

    @Test
    void testUniversiteConstructor() {
        // Arrange
        String expectedNomUniv = "ESPRIT University";

        // Act
        Universite universite = new Universite(expectedNomUniv);

        // Assert
        assertEquals(expectedNomUniv, universite.getNomUniv(), "The nomUniv should be correctly assigned.");
    }

    @Test
    void testRelationshipWithDepartement() {
        // Create instances for test
        Universite universite = new Universite();
        Set<Departement> departements = new HashSet<>();

        // Set relationship
        universite.setDepartements(departements);

        // Validate relationship
        assertEquals(departements, universite.getDepartements());
    }

    @Test
    void testParameterizedConstructor() {
        Integer idUniv = 1;
        String nomUniv = "ESPRIT";

        Universite universite = new Universite(idUniv, nomUniv);

        assertEquals(idUniv, universite.getIdUniv());
        assertEquals(nomUniv, universite.getNomUniv());
    }

    @Test
    void testAddDepartement() {
        // Create Universite and Departement instances
        Universite universite = new Universite("ESPRIT");
        Departement departement = new Departement("Computer Science");

        // Ensure departements is initialized
        universite.setDepartements(new HashSet<>());
        universite.getDepartements().add(departement);

        // Assert that the departement was added
        assertTrue(universite.getDepartements().contains(departement), "Departement should be added to Universite");
    }
}
