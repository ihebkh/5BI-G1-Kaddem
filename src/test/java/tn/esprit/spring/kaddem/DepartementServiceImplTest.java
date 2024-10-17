package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.entities.Departement;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;


    @Test
    void testRetrieveAllDepartements() {
        // Préparation des données de test
        Departement departement1 = new Departement();
        Departement departement2 = new Departement();
        List<Departement> expectedDepartements = Arrays.asList(departement1, departement2);

        // Simulation du comportement du repository
        when(departementRepository.findAll()).thenReturn(expectedDepartements);

        // Appel de la méthode de service
        List<Departement> actualDepartements = departementService.retrieveAllDepartements();

        // Vérification du résultat
        assertEquals(expectedDepartements, actualDepartements);
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveDepartement() {
        // ID du departement à récupérer
        Integer departementId = 1;
        Departement expectedDepartement = new Departement();

        // Simulation du comportement du repository
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(expectedDepartement));

        // Appel de la méthode de service
        Departement actualDepartement = departementService.retrieveDepartement(departementId);

        // Vérification du résultat
        assertEquals(expectedDepartement, actualDepartement);
        verify(departementRepository, times(1)).findById(departementId);
    }


    @Test
    void testRemoveDepartement() {
        // ID du departement à supprimer
        Integer departementId = 1;
        Departement departement = new Departement();

        // Simulation du comportement du repository
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        // Appel de la méthode de suppression
        departementService.deleteDepartement(departementId);

        // Vérification que la méthode delete a été appelée
        verify(departementRepository, times(1)).delete(departement);
    }



    @Test
    public void testRemoveEtudiantFromDepartement() {
        Integer etudiantId = 101;

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(etudiantId);
        Departement departement = new Departement();
        etudiant.setDepartement(departement);

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        departementService.removeEtudiantFromDepartement(etudiantId);

        // Verify that the student's department was set to null
        assertNull(etudiant.getDepartement());

        // Verify that the student was saved after being disassociated from the department
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    public void testRemoveEtudiantFromDepartementNotFound() {
        Integer etudiantId = 101;

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

        departementService.removeEtudiantFromDepartement(etudiantId);

        verify(etudiantRepository, never()).save(any(Etudiant.class));
    }





}