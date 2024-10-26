package tn.esprit.spring.kaddem.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratDTO {
        private String dateDebutContrat;
        private String dateFinContrat;
        private String specialite;
        private Boolean archive;
        private Integer montantContrat;
        private Integer etudiantId; // Id de l'étudiant associé

        // Getters et Setters pour chaque champ
    }


