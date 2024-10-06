package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.List;

@Slf4j

@Service
public class DepartementServiceImpl implements IDepartementService{
	@Autowired
	DepartementRepository departementRepository;
	public List<Departement> retrieveAllDepartements(){
		return (List<Departement>) departementRepository.findAll();
	}

	public Departement addDepartement (Departement d){
		log.info("Departemetn ajouté :\n" +
				"Nom d'Departement "+d.getIdDepart() +" " +
				"ID d'Departement " +d.getNomDepart()+"" );
		return departementRepository.save(d);
	}

	public   Departement updateDepartement (Departement d){
		log.info("modification d'un departement");
		return departementRepository.save(d);
	}

	public  Departement retrieveDepartement (Integer idDepart){
		log.info("Récupération d'un departement par ID");
		return departementRepository.findById(idDepart).get();
	}
	public  void deleteDepartement(Integer idDepartement){
		log.info("Suppression d'un departement par ID");
		Departement d=retrieveDepartement(idDepartement);
		departementRepository.delete(d);
	}



}
