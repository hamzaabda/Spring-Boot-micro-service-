package tn.esprit.reclamation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.reclamation.entity.Reclamation;
import tn.esprit.reclamation.repository.ReclamationRepository;

import java.util.List;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    public Reclamation createReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    public Reclamation updateReclamation(Reclamation updatedReclamation) {
        return reclamationRepository.save(updatedReclamation);
    }

    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }
}

