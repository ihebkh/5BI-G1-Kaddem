package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Contrat;

import java.util.List;


public interface IContratService {


    public Contrat retrieveContrat (Integer  idContrat);

    public  void removeContrat(Integer idContrat);

}

