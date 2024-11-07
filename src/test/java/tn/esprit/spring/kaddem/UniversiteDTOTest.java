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
        universiteDTO.setNomUniv("Engineering University");

        // Assert
        assertThat(universiteDTO.getIdUniv()).isEqualTo(1);
        assertThat(universiteDTO.getNomUniv()).isEqualTo("Engineering University");
    }

    @Test
    void testConstructorWithFields() {
        // Arrange & Act
        UniversiteDTO universiteDTO = new UniversiteDTO(1, "Engineering University");

        // Assert
        assertThat(universiteDTO.getIdUniv()).isEqualTo(1);
        assertThat(universiteDTO.getNomUniv()).isEqualTo("Engineering University");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UniversiteDTO universiteDTO1 = new UniversiteDTO(1, "Engineering University");
        UniversiteDTO universiteDTO2 = new UniversiteDTO(1, "Engineering University");

        // Assert
        assertThat(universiteDTO1).isEqualTo(universiteDTO2);
        assertThat(universiteDTO1.hashCode()).isEqualTo(universiteDTO2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        UniversiteDTO universiteDTO = new UniversiteDTO(1, "Esprit");

        // Act & Assert
        assertThat(universiteDTO.toString()).contains("idUniv=1", "nomUniv='Esprit'");
    }
}
