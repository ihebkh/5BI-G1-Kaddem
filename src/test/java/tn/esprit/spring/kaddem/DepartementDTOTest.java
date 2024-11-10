package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.DepartementDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DepartementDTOTest {
    @Test
    void testGettersAndSetters() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setIdDepart(1);
        departementDTO.setNomDepart("Computer Science");

        assertThat(departementDTO.getIdDepart()).isEqualTo(1);
        assertThat(departementDTO.getNomDepart()).isEqualTo("Computer Science");
    }
}
