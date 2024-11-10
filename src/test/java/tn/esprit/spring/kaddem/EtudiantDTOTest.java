package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.entities.Option;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class EtudiantDTOTest {
    @Test
    void testGettersAndSetters() {
        // Arrange
        EtudiantDTO etudiantDTO = new EtudiantDTO();

        // Act
        etudiantDTO.setIdEtudiant(1);
        etudiantDTO.setNomE("Yassine");
        etudiantDTO.setPrenomE("Ben Ali");
        etudiantDTO.setOp(Option.SE);
        etudiantDTO.setDepartementId(2);

        // Assert
        assertThat(etudiantDTO.getIdEtudiant()).isEqualTo(1);
        assertThat(etudiantDTO.getNomE()).isEqualTo("Yassine");
        assertThat(etudiantDTO.getPrenomE()).isEqualTo("Ben Ali");
        assertThat(etudiantDTO.getOp()).isEqualTo(Option.SE);
        assertThat(etudiantDTO.getDepartementId()).isEqualTo(2);
    }
    @Test
    void testConstructorWithFields() {
        // Arrange & Act
        EtudiantDTO etudiantDTO = new EtudiantDTO(1, "Yassine", "Ben Ali", Option.SE, 2);

        // Assert
        assertThat(etudiantDTO.getIdEtudiant()).isEqualTo(1);
        assertThat(etudiantDTO.getNomE()).isEqualTo("Yassine");
        assertThat(etudiantDTO.getPrenomE()).isEqualTo("Ben Ali");
        assertThat(etudiantDTO.getOp()).isEqualTo(Option.SE);
        assertThat(etudiantDTO.getDepartementId()).isEqualTo(2);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Set<Integer> contrats1 = new HashSet<>(Arrays.asList(101, 102));
        Set<Integer> contrats2 = new HashSet<>(Arrays.asList(103, 104));

        EtudiantDTO etudiantDTO1 = new EtudiantDTO(1, "Yassine", "Ben Ali", Option.SE, 2, Arrays.asList("Team A", "Team B"), contrats1);
        EtudiantDTO etudiantDTO2 = new EtudiantDTO(1, "Yassine", "Ben Ali", Option.SE, 2, Arrays.asList("Team A", "Team B"), contrats1);
        EtudiantDTO etudiantDTO3 = new EtudiantDTO(2, "Mohamed", "Ali", Option.GAMIX, 3, Arrays.asList("Team C", "Team D"), contrats2);

        // Assert: Equality check
        assertThat(etudiantDTO1).isEqualTo(etudiantDTO2);
        assertThat(etudiantDTO1.hashCode()).isEqualTo(etudiantDTO2.hashCode());

        // Assert: Inequality check
        assertThat(etudiantDTO1).isNotEqualTo(etudiantDTO3);
        assertThat(etudiantDTO1.hashCode()).isNotEqualTo(etudiantDTO3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Set<Integer> contrats = new HashSet<>(Arrays.asList(101, 102));
        EtudiantDTO etudiantDTO = new EtudiantDTO(1, "Yassine", "Ben Ali", Option.SE, 2, Arrays.asList("Team A", "Team B"), contrats);

        // Act & Assert
        String toStringResult = etudiantDTO.toString();
        assertThat(toStringResult).contains("idEtudiant=" + etudiantDTO.getIdEtudiant(), "nomE='" + etudiantDTO.getNomE() + "'", "prenomE='" + etudiantDTO.getPrenomE() + "'", "op=" + etudiantDTO.getOp());
        // This ensures the toString result contains the expected values
    }

}
