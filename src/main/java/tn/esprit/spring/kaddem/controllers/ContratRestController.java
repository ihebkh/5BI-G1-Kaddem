package tn.esprit.spring.kaddem.controllers;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.services.IContratService;


@RestController
@RequestMapping("/contrat")
public class ContratRestController {
	IContratService contratService;




	// http://localhost:8089/Kaddem/contrat/remove-contrat/1
	@DeleteMapping("/remove-contrat/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		contratService.removeContrat(contratId);
	}




}


