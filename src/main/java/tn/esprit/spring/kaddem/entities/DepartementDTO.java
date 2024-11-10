package tn.esprit.spring.kaddem.entities;

public class DepartementDTO {
    private Integer idDepart;
    private String nomDepart;

    // Getters and Setters
    public Integer getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(Integer idDepart) {
        this.idDepart = idDepart;
    }

    public String getNomDepart() {
        return nomDepart;
    }

    public void setNomDepart(String nomDepart) {
        this.nomDepart = nomDepart;
    }
}