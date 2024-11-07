package tn.esprit.spring.kaddem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.entities.UniversiteDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/universite")
public class UniversiteRestController {

	private final UniversiteServiceImpl universiteService;

	@Autowired
	public UniversiteRestController(UniversiteServiceImpl universiteService) {
		this.universiteService = universiteService;
	}

	@GetMapping("/retrieve-all-universites")
	public ResponseEntity<List<UniversiteDTO>> getAllUniversites() {
		List<Universite> listUniversites = universiteService.retrieveAllUniversites();
		List<UniversiteDTO> universiteDTOs = listUniversites.stream()
				.map(this::convertToDTO)
			 	.collect(Collectors.toList());
		return new ResponseEntity<>(universiteDTOs, HttpStatus.OK);
	}

	@GetMapping("/retrieve-universite/{universite-id}")
	public ResponseEntity<UniversiteDTO> getUniversiteById(@PathVariable("universite-id") Integer universiteId) {
		Universite universite = universiteService.retrieveUniversite(universiteId);
		if (universite != null) {
			return new ResponseEntity<>(convertToDTO(universite), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add-universite")
	public ResponseEntity<UniversiteDTO> createUniversite(@RequestBody UniversiteDTO universiteDTO) {
		Universite universite = convertToEntity(universiteDTO);
		Universite savedUniversite = universiteService.addUniversite(universite);
		return new ResponseEntity<>(convertToDTO(savedUniversite), HttpStatus.CREATED);
	}

	@PutMapping("/update-universite")
	public ResponseEntity<UniversiteDTO> updateUniversite(@RequestBody UniversiteDTO universiteDTO) {
		Universite universite = convertToEntity(universiteDTO);
		Universite updatedUniversite = universiteService.updateUniversite(universite);
		if (updatedUniversite != null) {
			return new ResponseEntity<>(convertToDTO(updatedUniversite), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/listerDepartementsUniversite/{idUniversite}")
	public ResponseEntity<Set<DepartementDTO>> getDepartementsByUniversite(@PathVariable("idUniversite") Integer idUniversite) {
		Set<Departement> departements = universiteService.retrieveDepartementsByUniversite(idUniversite);
		Set<DepartementDTO> departementDTOs = departements.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toSet());
		return new ResponseEntity<>(departementDTOs, HttpStatus.OK);
	}

	// Helper methods to convert between entities and DTOs
	private UniversiteDTO convertToDTO(Universite universite) {
		UniversiteDTO dto = new UniversiteDTO();
		dto.setIdUniv(universite.getIdUniv());
		dto.setNomUniv(universite.getNomUniv());
		dto.setDepartements(universite.getDepartements().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toSet()));
		return dto;
	}

	private Universite convertToEntity(UniversiteDTO dto) {
		Universite universite = new Universite();
		universite.setIdUniv(dto.getIdUniv());
		universite.setNomUniv(dto.getNomUniv());
		return universite;
	}

	private DepartementDTO convertToDTO(Departement departement) {
		DepartementDTO dto = new DepartementDTO();
		dto.setIdDepart(departement.getIdDepart());
		dto.setNomDepart(departement.getNomDepart());
		return dto;
	}


}
