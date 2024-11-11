package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService{
	private final EtudiantRepository etudiantRepository;
	private final ContratRepository contratRepository;
	private final EquipeRepository equipeRepository;
	private final DepartementRepository departementRepository;

	@Autowired
	public EtudiantServiceImpl(EtudiantRepository etudiantRepository,
							   ContratRepository contratRepository,
							   EquipeRepository equipeRepository,
							   DepartementRepository departementRepository) {
		this.etudiantRepository = etudiantRepository;
		this.contratRepository = contratRepository;
		this.equipeRepository = equipeRepository;
		this.departementRepository = departementRepository;
	}
	public List<Etudiant> retrieveAllEtudiants() {
		// Use the injected repository instance to call findAll()
		return StreamSupport.stream(etudiantRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	public Etudiant addEtudiant (Etudiant e){
		return etudiantRepository.save(e);
	}

	public Etudiant updateEtudiant (Etudiant e){

		Optional<Etudiant> existingEtudiant = etudiantRepository.findById(e.getIdEtudiant());

		if (existingEtudiant.isPresent()) {
			Etudiant etudiantToUpdate = existingEtudiant.get();
			etudiantToUpdate.setNomE(e.getNomE());
			etudiantToUpdate.setPrenomE(e.getPrenomE());
			// Add other fields to update as necessary
			return etudiantRepository.save(etudiantToUpdate);
		} else {
			throw new EtudiantNotFoundException("Etudiant with ID " + e.getIdEtudiant() + " not found");
		}
	}

	public Etudiant retrieveEtudiant(Integer  idEtudiant){
		return etudiantRepository.findById(idEtudiant).orElse(null);
	}

	public void removeEtudiant(Integer idEtudiant){
		etudiantRepository.deleteById(idEtudiant);
	}

	public void assignEtudiantToDepartement (Integer etudiantId, Integer departementId){
		if (departementId == null) {
			throw new IllegalArgumentException("Department ID cannot be null");
		}

		Etudiant etudiant = etudiantRepository.findById(etudiantId)
				.orElseThrow(() -> new EtudiantNotFoundException("Etudiant with ID " + etudiantId + " not found"));

		Departement departement = departementRepository.findById(departementId)
				.orElseThrow(() -> new DepartementNotFoundException("Departement with ID " + departementId + " not found"));

		etudiant.setDepartement(departement);
		etudiantRepository.save(etudiant);
	}
	@Transactional
	public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe){
		Contrat c = contratRepository.findById(idContrat).orElseThrow(() -> new IllegalArgumentException("Contrat not found"));
		Equipe eq = equipeRepository.findById(idEquipe).orElseThrow(() -> new IllegalArgumentException("Equipe not found"));

		c.setEtudiant(e);
		eq.getEtudiants().add(e);
		etudiantRepository.save(e);  // Ensure the etudiant is saved as well

		return e;
	}

	public 	List<Etudiant> getEtudiantsByDepartement (Integer idDepartement){
return  etudiantRepository.findEtudiantsByDepartement_IdDepart((idDepartement));
	}
	public class EtudiantNotFoundException extends RuntimeException {
		public EtudiantNotFoundException(String message) {
			super(message);
		}
	}

	public class DepartementNotFoundException extends RuntimeException {
		public DepartementNotFoundException(String message) {
			super(message);
		}
	}

}
