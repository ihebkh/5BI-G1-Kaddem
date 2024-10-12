package tn.esprit.spring.kaddem.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;


import java.util.List;


@RestController
@RequestMapping("/contrat")
public class ContratRestController {
	@Autowired // Vérifiez que cette annotation est présente
	private IContratService contratService;

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

	@Scheduled(cron="0 0 13 * * *")//(cron="0 0 13 * * ?")(fixedRate =21600)
	@PutMapping(value = "/majStatusContrat")
	public void majStatusContrat (){
		contratService.retrieveAndUpdateStatusContrat();

	}

	@PostMapping("/add-contrat")
	public Contrat addContrat(@RequestBody Contrat c) {
		Contrat contrat = new Contrat();
		contrat.setIdContrat(c.getIdContrat());
		contrat.setDateDebutContrat(c.getDateDebutContrat());
		contrat.setDateFinContrat(c.getDateFinContrat());
		contrat.setSpecialite(c.getSpecialite());
		contrat.setArchive(c.getArchive());
		contrat.setMontantContrat(c.getMontantContrat());
		return contrat;
	}
}




