package tn.esprit.spring.kaddem.controllers;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contrat")
public class ContratRestController {
	IContratService contratService;
	// http://localhost:8089/Kaddem/contrat/retrieve-all-contrats
	@GetMapping("/retrieve-all-contrats")
	public List<Contrat> getContrats() {
		return   contratService.retrieveAllContrats();
	}
	// http://localhost:8089/Kaddem/contrat/retrieve-contrat/8
	@GetMapping("/retrieve-contrat/{contrat-id}")
	public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
		return contratService.retrieveContrat(contratId);
	}

	@PostMapping("/add-contrat")
	public Contrat addContrat(@RequestBody Contrat c) {
		return contratService.addContrat(c);
	}

	// http://localhost:8089/Kaddem/contrat/remove-contrat/1
	@DeleteMapping("/remove-contrat/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		contratService.removeContrat(contratId);
	}

	// http://localhost:8089/Kaddem/contrat/update-contrat
	@PutMapping("/update-contrat")
	public Contrat updateContrat(@RequestBody Contrat c) {
		return  contratService.updateContrat(c);

	}

}


