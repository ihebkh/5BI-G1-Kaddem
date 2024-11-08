package tn.esprit.spring.kaddem.entities;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class EtudiantDTO {
    private Integer idEtudiant;
    private String nomE;
    private String prenomE;
    private Option op;
    private Integer departementId; // Instead of full Departement, just use ID or name
    private List<String> equipeNames; // List of names for the teams (equipes)
    private Set<Integer> contratIds; // Instead of full Contrat, just use contract IDs

    // Default no-arg constructor
    public EtudiantDTO() {}

    // Parameterized constructor
    public EtudiantDTO(Integer idEtudiant, String nomE, String prenomE, Option op, Integer departementId, List<String> equipeNames, Set<Integer> contratIds) {
        this.idEtudiant = idEtudiant;
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.op = op;
        this.departementId = departementId;
        this.equipeNames = equipeNames;
        this.contratIds = contratIds;
    }

    // Constructeur correspondant à l'exigence du test
    public EtudiantDTO(Integer idEtudiant, String nomE, String prenomE, Option op, Integer departementId) {
        this.idEtudiant = idEtudiant;
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.op = op;
        this.departementId = departementId;
    }


    // Getters and Setters
    public Integer getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Integer idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNomE() {
        return nomE;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    public String getPrenomE() {
        return prenomE;
    }

    public void setPrenomE(String prenomE) {
        this.prenomE = prenomE;
    }

    public Option getOp() {
        return op;
    }

    public void setOp(Option op) {
        this.op = op;
    }

    public Integer getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Integer departementId) {
        this.departementId = departementId;
    }

    public List<String> getEquipeNames() {
        return equipeNames;
    }

    public void setEquipeNames(List<String> equipeNames) {
        this.equipeNames = equipeNames;
    }

    public Set<Integer> getContratIds() {
        return contratIds;
    }

    public void setContratIds(Set<Integer> contratIds) {
        this.contratIds = contratIds;
    }

    // equals and hashCode based on the idEtudiant field
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EtudiantDTO that = (EtudiantDTO) obj;
        return Objects.equals(idEtudiant, that.idEtudiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtudiant);
    }

    // toString method
    @Override
    public String toString() {
        return "EtudiantDTO{idEtudiant=" + idEtudiant + ", nomE='" + nomE + "', prenomE='" + prenomE + "', op=" + op + "}";
    }
}
