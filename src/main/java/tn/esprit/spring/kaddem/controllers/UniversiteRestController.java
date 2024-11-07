package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.services.IUniversiteService;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/universite")
public class UniversiteRestController {

	@Autowired
	private IUniversiteService universiteService;

	// Endpoint to retrieve all universities
	@GetMapping("/retrieve-all-universites")
	public ResponseEntity<List<Universite>> getAllUniversites() {
		List<Universite> listUniversites = universiteService.retrieveAllUniversites();
		return new ResponseEntity<>(listUniversites, HttpStatus.OK);
	}

//http://localhost:8089/kaddem/universite/retrieve-universite/1
	@GetMapping("/retrieve-universite/{universite-id}")
	public ResponseEntity<Universite> getUniversiteById(@PathVariable("universite-id") Integer universiteId) {
		Universite universite = universiteService.retrieveUniversite(universiteId);
		if (universite != null) {
			return new ResponseEntity<>(universite, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Endpoint to add a new university
	@PostMapping("/add-universite")
	public ResponseEntity<Universite> createUniversite(@RequestBody Universite universite) {
		Universite savedUniversite = universiteService.addUniversite(universite);
		return new ResponseEntity<>(savedUniversite, HttpStatus.CREATED);
	}

	// Endpoint to remove a university by its ID
	@DeleteMapping("/remove-universite/{universite-id}")
	public ResponseEntity<Void> removeUniversite(@PathVariable("universite-id") Integer universiteId) {
		universiteService.removeUniversite(universiteId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Endpoint to update an existing university
	@PutMapping("/update-universite")
	public ResponseEntity<Universite> updateUniversite(@RequestBody Universite universite) {
		Universite updatedUniversite = universiteService.updateUniversite(universite);
		if (updatedUniversite != null) {
			return new ResponseEntity<>(updatedUniversite, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Endpoint to assign a department to a university
	@PutMapping("/affecter-universite-departement/{universiteId}/{departementId}")
	public ResponseEntity<Void> assignUniversiteToDepartement(
			@PathVariable("universiteId") Integer universiteId,
			@PathVariable("departementId") Integer departementId) {
		universiteService.assignUniversiteToDepartement(universiteId, departementId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Endpoint to list departments of a specific university
	@GetMapping("/listerDepartementsUniversite/{idUniversite}")
	public ResponseEntity<Set<Departement>> getDepartementsByUniversite(@PathVariable("idUniversite") Integer idUniversite) {
		Set<Departement> departements = universiteService.retrieveDepartementsByUniversite(idUniversite);
		return new ResponseEntity<>(departements, HttpStatus.OK);
	}
}
