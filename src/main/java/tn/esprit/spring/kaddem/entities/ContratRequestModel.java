package tn.esprit.spring.kaddem.entities;

import java.util.Date;

public class ContratRequestModel {
    private Date dateDebutContrat;
    private Date dateFinContrat;
    private String specialite;  // Will be converted to Enum in the controller
    private Boolean archive;
    private Integer montantContrat;

    // Default constructor
    public ContratRequestModel() {
    }

    // Parameterized constructor
    public ContratRequestModel(Date dateDebutContrat, Date dateFinContrat, String specialite, Boolean archive, Integer montantContrat) {
        this.dateDebutContrat = dateDebutContrat;
        this.dateFinContrat = dateFinContrat;
        this.specialite = specialite;
        this.archive = archive;
        this.montantContrat = montantContrat;
    }

    // Getters and Setters
    public Date getDateDebutContrat() {
        return dateDebutContrat;
    }

    public void setDateDebutContrat(Date dateDebutContrat) {
        this.dateDebutContrat = dateDebutContrat;
    }

    public Date getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(Date dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Integer getMontantContrat() {
        return montantContrat;
    }

    public void setMontantContrat(Integer montantContrat) {
        this.montantContrat = montantContrat;
    }
}
