package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.EquipeDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EquipeDTOTest {
    @Test
    void testGettersAndSetters() {
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setIdEquipe(1);
        equipeDTO.setNomEquipe("Dream Team");
        equipeDTO.setNiveau("Advanced");

        assertThat(equipeDTO.getIdEquipe()).isEqualTo(1);
        assertThat(equipeDTO.getNomEquipe()).isEqualTo("Dream Team");
        assertThat(equipeDTO.getNiveau()).isEqualTo("Advanced");
    }
}
