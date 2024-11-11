package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;

import tn.esprit.spring.kaddem.entities.Etudiant;

import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {
    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;


    @Test
    void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = Collections.singletonList(new Etudiant("Etudiant: Nom: A", "Prenom : B"));
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertEquals(1, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }
    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant("Etudiant: Nom: A");
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);

        assertNotNull(result);
        assertEquals("Etudiant: Nom: A", result.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant);
    }
    @Test
    void testAddEtudiantMissingNom() {
        Etudiant etudiant = new Etudiant();
        etudiant.setPrenomE("John");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.addEtudiant(etudiant);
        });

        assertEquals("Nom is required", exception.getMessage());
    }
    @Test
    void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant(1, "Etudiant A");
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant)); // Stub findById

        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.updateEtudiant(etudiant);

        assertNotNull(result);
        assertEquals("Etudiant A", result.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRetrieveEtudiant() {
        Integer idEtudiant = 1;
        Etudiant etudiant = new Etudiant(idEtudiant, "Etudiant A");
        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(idEtudiant);

        assertNotNull(result);
        assertEquals("Etudiant A", result.getNomE());
        verify(etudiantRepository, times(1)).findById(idEtudiant);
    }

    @Test
    void testRetrieveNonExistentEtudiant() {
        Integer idEtudiant = 999;
        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.empty());

        Etudiant result = etudiantService.retrieveEtudiant(idEtudiant);

        assertNull(result, "Expected null when retrieving a non-existent student.");
        verify(etudiantRepository, times(1)).findById(idEtudiant);
    }

    @Test
    void testRemoveEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);

        doNothing().when(etudiantRepository).deleteById(1);

        etudiantService.removeEtudiant(1);

        verify(etudiantRepository, times(1)).deleteById(1);
    }

    @Test
    void testRemoveNonExistentEtudiant() {
        doThrow(new RuntimeException("Not found")).when(etudiantRepository).deleteById(999);

        assertThrows(RuntimeException.class, () -> etudiantService.removeEtudiant(999),
                "Expected a RuntimeException when deleting a non-existent university.");

        verify(etudiantRepository, times(1)).deleteById(999);
    }

    @Test
    void testAssignEtudiantToDepartement() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        Departement departement = new Departement();
        departement.setIdDepart(1);

        // Simuler le retour des méthodes de repository
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        etudiantService.assignEtudiantToDepartement(1, 1);

        // Assert
        verify(etudiantRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).save(etudiant);  // Vérifier si la sauvegarde a été appelée
        assertEquals(departement, etudiant.getDepartement());  // Vérifier que le département a été correctement assigné
    }
    @Test
    void testAddAndAssignEtudiantToEquipeAndContract() {

        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("Test");
        etudiant.setPrenomE("Etudiant");

        // Créer un contrat
        Contrat contrat = new Contrat();
        contrat.setIdContrat(101);
        contrat.setEtudiant(etudiant);

        // Créer un Set de contrats et ajouter le contrat
        Set<Contrat> contrats = new HashSet<>();
        contrats.add(contrat);

        // Assigner le Set de contrats à l'étudiant
        etudiant.setContrats(contrats);

        // Simuler la sauvegarde de l'étudiant avec la méthode addEtudiant
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Appel du service pour ajouter l'étudiant
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant); // Remplacer saveEtudiant par addEtudiant

        // Vérifier que le contrat a bien été assigné à l'étudiant sauvegardé
        Set<Contrat> contratsResultats = savedEtudiant.getContrats();

        // Assertions pour vérifier les résultats
        assertNotNull(contratsResultats, "Les contrats ne doivent pas être nuls");
        assertEquals(1, contratsResultats.size(), "Il doit y avoir un contrat assigné");

        Contrat contratResultat = contratsResultats.iterator().next(); // Récupérer le contrat du Set

        assertEquals(101, contratResultat.getIdContrat(), "L'ID du contrat doit être 101");
        assertEquals(etudiant, contratResultat.getEtudiant(), "L'étudiant associé au contrat doit être le même");
    }

    @Test
    void testGetEtudiantsByDepartement() {
        // Arrange
        List<Etudiant> etudiants = Collections.singletonList(new Etudiant("Etudiant A", "Prenom A"));
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(2)).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantService.getEtudiantsByDepartement(2);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Etudiant A", result.get(0).getNomE());
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(2);
    }
    @Test
    void testAssignEtudiantToNonExistentDepartement() {
        // Arrange
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(new Etudiant()));
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> etudiantService.assignEtudiantToDepartement(1, 999),
                "Expected a RuntimeException when assigning to a non-existent department");

        verify(etudiantRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(999);
        verify(etudiantRepository, never()).save(any(Etudiant.class)); // Ensure save is not called
    }
    @Test
    void testRetrieveAllEtudiantsWhenEmpty() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Assert
        assertTrue(result.isEmpty(), "Expected no students in the list");
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testUpdateNonExistentEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant(999, "NonExistent");
        when(etudiantRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> etudiantService.updateEtudiant(etudiant),
                "Expected a RuntimeException when updating a non-existent student");

        verify(etudiantRepository, times(1)).findById(999);
        verify(etudiantRepository, never()).save(any(Etudiant.class)); // Ensure save is not called
    }
    @Test
    void testAssignEtudiantToNullDepartement() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> etudiantService.assignEtudiantToDepartement(1, null),
                "Expected IllegalArgumentException when assigning to a null department");

        // No interactions with repositories should occur
        verify(etudiantRepository, never()).findById(anyInt());
        verify(departementRepository, never()).findById(any());
    }



}
