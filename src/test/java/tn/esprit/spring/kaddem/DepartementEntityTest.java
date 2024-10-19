package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DepartementEntityTest {

    @Test
    void testGettersAndSetters() {
        // Create instances for test
        Departement departement = new Departement();
        String nomDep = "TECH";


        // Set values
        departement.setNomDepart(nomDep);



        // Validate values with getters
        assertEquals(nomDep,departement.getNomDepart());

    }

    @Test
    void testDepartementConstructor() {
        // Arrange
        String expectedNomDepart = "Finance";

        // Act
        Departement departement = new Departement(expectedNomDepart);

        // Assert
        assertEquals(expectedNomDepart, departement.getNomDepart(), "The nomDepart should be correctly assigned.");
    }

    @Test
    void testRelationshipWithEtudiant() {
        // Create instances for test
        Departement departement = new Departement();
        Set<Etudiant> etudiants = new HashSet<>();

        // Set relationship
        departement.setEtudiants(etudiants);

        // Validate relationship
        assertEquals(etudiants, departement.getEtudiants());
    }
    @Test
    void testParameterizedConstructor() {
        Integer idDep = 1;
        String nomDep = "TECH";


        Departement departement = new Departement(idDep,nomDep);

        assertEquals(idDep, departement.getIdDepart());
        assertEquals(nomDep, departement.getNomDepart());

    }


    /*@Test
    void testToStringMethod() {
        // Prepare sample data

        Departement departement = new Departement();
        String nomDep = "TECH";
        departement.setIdDepart(1);
        departement.setNomDepart(nomDep);


        // Validate toString output
        String departementString = departement.toString();
        assertTrue(departementString.contains("idDepart=1"));
        assertTrue(departementString.contains("nomDep=" + nomDep));

    }*/
}