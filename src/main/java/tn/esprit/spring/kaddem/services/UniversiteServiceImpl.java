package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UniversiteServiceImpl implements IUniversiteService {

    private final UniversiteRepository universiteRepository;
    private final DepartementRepository departementRepository;

    // Constructor injection
    public UniversiteServiceImpl(UniversiteRepository universiteRepository, DepartementRepository departementRepository) {
        this.universiteRepository = universiteRepository;
        this.departementRepository = departementRepository;
    }

    @Override
    public List<Universite> retrieveAllUniversites() {
        // Convert Iterable to List using Stream
        return StreamSupport.stream(universiteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(Integer idUniversite) {
        return universiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public void deleteUniversite(Integer idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new IllegalArgumentException("University not found"));
        Departement departement = departementRepository.findById(idDepartement)
                .orElseThrow(() -> new IllegalArgumentException("Departement not found"));

        universite.getDepartements().add(departement);

        universiteRepository.save(universite);
    }

    @Override
    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite != null) {
            return universite.getDepartements();
        }
        // Return an empty set instead of null
        return Collections.emptySet();
    }

    @Override
    public void removeUniversite(Integer idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }
}
