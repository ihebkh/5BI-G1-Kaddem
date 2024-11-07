// UniversiteDTO.java
package tn.esprit.spring.kaddem.entities;

import java.util.Objects;
import java.util.Set;

public class UniversiteDTO {
    private Integer idUniv;
    private String nomUniv;
    private Set<DepartementDTO> departements;

    // Default no-arg constructor
    public UniversiteDTO() {}

    // Parameterized constructor
    public UniversiteDTO(Integer idUniv, String nomUniv) {
        this.idUniv = idUniv;
        this.nomUniv = nomUniv;
    }




    // Getters and Setters
    public Integer getIdUniv() {
        return idUniv;
    }

    public void setIdUniv(Integer idUniv) {
        this.idUniv = idUniv;
    }

    public String getNomUniv() {
        return nomUniv;
    }

    public void setNomUniv(String nomUniv) {
        this.nomUniv = nomUniv;
    }

    public Set<DepartementDTO> getDepartements() {
        return departements;
    }

    public void setDepartements(Set<DepartementDTO> departements) {
        this.departements = departements;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UniversiteDTO that = (UniversiteDTO) obj;
        return idUniv == that.idUniv && nomUniv.equals(that.nomUniv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUniv, nomUniv);
    }


    @Override
    public String toString() {
        return "UniversiteDTO{idUniv=" + idUniv + ", nomUniv='" + nomUniv + "'}";
    }

}
