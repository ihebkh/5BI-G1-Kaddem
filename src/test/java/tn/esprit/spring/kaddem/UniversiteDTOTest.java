package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.UniversiteDTO;

import static org.assertj.core.api.Assertions.assertThat;

class UniversiteDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        UniversiteDTO universiteDTO = new UniversiteDTO();

        // Act
        universiteDTO.setIdUniv(1);
        universiteDTO.setNomUniv("esprit");

        // Assert
        assertThat(universiteDTO.getIdUniv()).isEqualTo(1);
        assertThat(universiteDTO.getNomUniv()).isEqualTo("esprit");
    }

    @Test
    void testConstructorWithFields() {
        // Arrange & Act
        UniversiteDTO universiteDTO = new UniversiteDTO(1, "esprit");

        // Assert
        assertThat(universiteDTO.getIdUniv()).isEqualTo(1);
        assertThat(universiteDTO.getNomUniv()).isEqualTo("esprit");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UniversiteDTO universiteDTO1 = new UniversiteDTO(1, "esprit");
        UniversiteDTO universiteDTO2 = new UniversiteDTO(1, "esprit");
        UniversiteDTO universiteDTO3 = new UniversiteDTO(2, "polytech");

        // Assert: Equality check
        assertThat(universiteDTO1).isEqualTo(universiteDTO2);
        assertThat(universiteDTO1.hashCode()).isEqualTo(universiteDTO2.hashCode());

        // Assert: Inequality check
        assertThat(universiteDTO1).isNotEqualTo(universiteDTO3);
        assertThat(universiteDTO1.hashCode()).isNotEqualTo(universiteDTO3.hashCode());
    }


    @Test
    void testToString() {
        // Arrange
        UniversiteDTO universiteDTO = new UniversiteDTO(1, "esprit");

        // Act & Assert
        String toStringResult = universiteDTO.toString();
        assertThat(toStringResult).contains("idUniv=" + universiteDTO.getIdUniv(), "nomUniv='" + universiteDTO.getNomUniv() + "'");
        // This makes sure the toString result contains the expected values
    }

}
