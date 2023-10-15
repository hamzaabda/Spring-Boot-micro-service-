package tn.esprit.reclamation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.reclamation.entity.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

}
