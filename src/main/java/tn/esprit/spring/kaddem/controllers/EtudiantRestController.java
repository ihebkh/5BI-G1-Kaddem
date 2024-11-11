package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
public class EtudiantRestController {
	@Autowired
	IEtudiantService etudiantService;
	// http://localhost:8089/Kaddem/etudiant/retrieve-all-etudiants
	@GetMapping("/retrieve-all-etudiants")
	public ResponseEntity<List<EtudiantDTO>> getAllEtudiants() {
		List<Etudiant> listEtudiants = etudiantService.retrieveAllEtudiants();
		List<EtudiantDTO> etudiantDTOs = listEtudiants.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(etudiantDTOs, HttpStatus.OK);
	}
	// http://localhost:8089/Kaddem/etudiant/retrieve-etudiant/8
	@GetMapping("/retrieve-etudiant/{etudiant-id}")
	public ResponseEntity<EtudiantDTO> getEtudiantById(@PathVariable("etudiant-id") Integer etudiantId) {
		Etudiant etudiant = etudiantService.retrieveEtudiant(etudiantId);
		if (etudiant != null) {
			return new ResponseEntity<>(convertToDTO(etudiant), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// http://localhost:8089/Kaddem/etudiant/add-etudiant
	@PostMapping("/add-etudiant")
	public ResponseEntity<EtudiantDTO> createEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
		Etudiant etudiant = convertToEntity(etudiantDTO);
		Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);
		return new ResponseEntity<>(convertToDTO(savedEtudiant), HttpStatus.CREATED);
	}

	// http://localhost:8089/Kaddem/etudiant/remove-etudiant/1
	@DeleteMapping("/remove-etudiant/{etudiant-id}")
	public void removeEtudiant(@PathVariable("etudiant-id") Integer etudiantId) {
		etudiantService.removeEtudiant(etudiantId);
	}

	// http://localhost:8089/Kaddem/etudiant/update-etudiant
	@PutMapping("/update-etudiant")
	public ResponseEntity<EtudiantDTO> updateEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
		Etudiant etudiant = convertToEntity(etudiantDTO);
		Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);
		if (updatedEtudiant != null) {
			return new ResponseEntity<>(convertToDTO(updatedEtudiant), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//@PutMapping("/affecter-etudiant-departement")
	@PutMapping(value="/affecter-etudiant-departement/{etudiantId}/{departementId}")
	public ResponseEntity<EtudiantDTO> affecterEtudiantToDepartement(@PathVariable("etudiantId") Integer etudiantId, @PathVariable("departementId") Integer departementId) {
		etudiantService.assignEtudiantToDepartement(etudiantId, departementId);
		Etudiant etudiant = etudiantService.retrieveEtudiant(etudiantId); // Récupérer l'étudiant après l'affectation
		if (etudiant != null) {
			return new ResponseEntity<>(convertToDTO(etudiant), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add-assign-Etudiant/{idContrat}/{idEquipe}")
	public ResponseEntity<EtudiantDTO> addEtudiantWithEquipeAndContract(@RequestBody EtudiantDTO etudiantDTO, @PathVariable("idContrat") Integer idContrat, @PathVariable("idEquipe") Integer idEquipe) {
		Etudiant etudiant = convertToEntity(etudiantDTO);
		Etudiant savedEtudiant = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, idContrat, idEquipe);
		return new ResponseEntity<>(convertToDTO(savedEtudiant), HttpStatus.CREATED);
	}

	@GetMapping(value = "/getEtudiantsByDepartement/{idDepartement}")
	public ResponseEntity<List<EtudiantDTO>> getEtudiantsParDepartement(@PathVariable("idDepartement") Integer idDepartement) {
		List<Etudiant> etudiants = etudiantService.getEtudiantsByDepartement(idDepartement);
		List<EtudiantDTO> etudiantDTOs = etudiants.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(etudiantDTOs, HttpStatus.OK);
	}
	// Helper methods to convert between entities and DTOs
	private EtudiantDTO convertToDTO(Etudiant etudiant) {
		EtudiantDTO dto = new EtudiantDTO();
		dto.setIdEtudiant(etudiant.getIdEtudiant());
		dto.setNomE(etudiant.getNomE());
		dto.setPrenomE(etudiant.getPrenomE());

		return dto;
	}

	private Etudiant convertToEntity(EtudiantDTO dto) {
		Etudiant etudiant = new Etudiant();
		etudiant.setIdEtudiant(dto.getIdEtudiant());
		etudiant.setNomE(dto.getNomE());
		etudiant.setPrenomE(dto.getPrenomE());
		return etudiant;
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}


