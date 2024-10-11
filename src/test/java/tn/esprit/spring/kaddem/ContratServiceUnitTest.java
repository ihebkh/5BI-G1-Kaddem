package tn.esprit.spring.kaddem;

import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratControllerTest {

    @Mock
    ContratServiceImpl contratService;

    @InjectMocks
    ContratRestController contratController;

    @Test
    void testRemoveContrat() {
        // ID du contrat à supprimer
        Integer contratId = 1;

        // Appel de la méthode de suppression du contrôleur
        contratController.removeContrat(contratId);

        // Vérification que la méthode removeContrat du service a été appelée avec le bon ID
        verify(contratService, times(1)).removeContrat(contratId);
    }
}
