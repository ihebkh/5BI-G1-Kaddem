package tn.esprit.spring.kaddem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.ContratDTO;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.IContratService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/contrat")
public class ContratRestController {

	@Autowired
	private IContratService contratService;
    @Autowired
    private EtudiantRepository etudiantRepository;

	@DeleteMapping("/remove-contrat/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		contratService.removeContrat(contratId);
	}

	@GetMapping("/retrieve-all-contrats")
	public List<Contrat> getContrats() {
		return contratService.retrieveAllContrats();
	}

	@GetMapping("/retrieve-contrat/{contrat-id}")
	public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
		return contratService.retrieveContrat(contratId);
	}

	@PutMapping(value = "/assignContratToEtudiant/{idContrat}/{nomE}/{prenomE}")
	public Contrat assignContratToEtudiant(
			@PathVariable Integer idContrat,
			@PathVariable String nomE,
			@PathVariable String prenomE) {
		return contratService.affectContratToEtudiant(idContrat, nomE, prenomE);
	}

	@Scheduled(cron="0 0 13 * * *")
	@PutMapping(value = "/majStatusContrat")
	public void majStatusContrat() {
		contratService.retrieveAndUpdateStatusContrat();
	}
	@PostMapping("/add-contrat")
	public Contrat addContrat(@RequestBody ContratDTO contratDTO) {
		// Convertir ContratDTO en Contrat
		Contrat contrat = new Contrat();
		contrat.setDateDebutContrat(Date.valueOf(contratDTO.getDateDebutContrat())); // Conversion de String en Date
		contrat.setDateFinContrat(Date.valueOf(contratDTO.getDateFinContrat()));
		contrat.setSpecialite(Specialite.valueOf(contratDTO.getSpecialite())); // Assurez-vous que la valeur existe dans l'énumération Specialite
		contrat.setArchive(contratDTO.getArchive());
		contrat.setMontantContrat(contratDTO.getMontantContrat());

		// Associer l'étudiant si nécessaire
		if (contratDTO.getEtudiantId() != null) {
			Etudiant etudiant = etudiantRepository.findById(contratDTO.getEtudiantId())
					.orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
			contrat.setEtudiant(etudiant);
		}

		return contratService.addContrat(contrat);
	}



}
