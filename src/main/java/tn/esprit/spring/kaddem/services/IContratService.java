package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Contrat;

import java.util.List;

public interface IContratService {

     List<Contrat> retrieveAllContrats();

     Contrat retrieveContrat (Integer  idContrat);

      void removeContrat(Integer idContrat);

     Contrat affectContratToEtudiant (Integer idContrat, String nomE, String prenomE);

}

