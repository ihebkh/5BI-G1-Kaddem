package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Contrat;


public interface IContratService {


    public Contrat retrieveContrat (Integer  idContrat);

    public  void removeContrat(Integer idContrat);


}

