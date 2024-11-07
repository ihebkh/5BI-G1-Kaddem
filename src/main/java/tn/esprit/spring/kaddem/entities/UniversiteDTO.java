// UniversiteDTO.java
package tn.esprit.spring.kaddem.entities;

import java.util.Set;

public class UniversiteDTO {
    private Integer idUniv;
    private String nomUniv;
    private Set<DepartementDTO> departements;

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
}
