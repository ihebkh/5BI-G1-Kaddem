package tn.esprit.spring.kaddem.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.List;
import java.util.Set;

@Slf4j

@Service
public class DepartementServiceImpl implements IDepartementService{
	DepartementRepository departementRepository;
	EtudiantRepository etudiantRepository;
	public List<Departement> retrieveAllDepartements(){
		return (List<Departement>) departementRepository.findAll();
	}


	public  Departement retrieveDepartement (Integer idDepart){
		log.info("Récupération d'un departement par ID");
		return departementRepository.findById(idDepart).orElse(null);
	}
	public  void deleteDepartement(Integer idDepartement){
		log.info("Suppression d'un departement par ID");
		Departement d=retrieveDepartement(idDepartement);
		departementRepository.delete(d);
	}

	// Method to affect a Departement to a list of Etudiants
	public Departement affectDepartementToEtudiants(Integer departementId, List<Integer> etudiantIds) {
		Departement departement = retrieveDepartement(departementId); // Retrieve the departement
		List<Etudiant> etudiants = (List<Etudiant>) etudiantRepository.findAllById(etudiantIds); // Retrieve students

		for (Etudiant etudiant : etudiants) {
			etudiant.setDepartement(departement);
		}

		etudiantRepository.saveAll(etudiants); // Save the updated students
		log.info("Affected departement ID {} to students: {}", departementId, etudiantIds);

		return departement;
	}

	public void removeEtudiantFromDepartement(Integer etudiantId) {
		Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
		if (etudiant != null) {
			etudiant.setDepartement(null); // Disassociate the department
			etudiantRepository.save(etudiant); // Save the updated student
			log.info("Removed student ID {} from their departement", etudiantId);
		} else {
			log.warn("Etudiant with ID {} not found", etudiantId);
		}
	}



}
