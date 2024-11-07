package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class EtudiantServiceTest {
    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

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
    void testUpdateEtudiant() {
        // Créer un étudiant existant avec un ID connu
        Etudiant existingEtudiant = new Etudiant(4, "Nom: A", "Prenom: B"); // Assurez-vous que l'ID existe
        when(etudiantRepository.findById(4)).thenReturn(Optional.of(existingEtudiant)); // Simuler l'existence de l'étudiant avec l'ID 4
        when(etudiantRepository.save(existingEtudiant)).thenReturn(existingEtudiant); // Simuler la mise à jour

        // Créer un étudiant à mettre à jour
        Etudiant updatedEtudiant = new Etudiant(4, "Nom: A", "Prenom: B Updated");

        // Appeler la méthode de mise à jour
        Etudiant result = etudiantService.updateEtudiant(updatedEtudiant);

        // Vérifier que l'étudiant a bien été mis à jour
        assertNotNull(result);
        assertEquals("Nom: A", result.getNomE());
        assertEquals("Prenom: B Updated", result.getPrenomE()); // Assurez-vous que le prénom a été mis à jour
        verify(etudiantRepository, times(1)).save(existingEtudiant); // Vérifier que save a été appelé une fois
    }
}
