package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UniversiteServiceImpl implements IUniversiteService {

    @Autowired
    private UniversiteRepository universiteRepository;

    @Autowired
    private DepartementRepository departementRepository;

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
        universiteRepository.deleteById(idUniversite);  // Deletes the Universite by its ID
    }

    @Override
    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        Universite universite = universiteRepository.findById(idUniversite).orElseThrow(() -> new IllegalArgumentException("University not found"));
        Departement departement = departementRepository.findById(idDepartement).orElseThrow(() -> new IllegalArgumentException("Departement not found"));

        // Ensure departements set is initialized (already done in the entity constructor)
        universite.getDepartements().add(departement);

        universiteRepository.save(universite);  // Save after adding departement
    }



    @Override
    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite != null) {
            return universite.getDepartements();
        }
        return null;
    }


    @Override
    public void removeUniversite(Integer idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }


}
