package tn.esprit.reclamation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.reclamation.entity.EventFeedback;

public interface EventFeedbackRepository extends JpaRepository<EventFeedback, Long> {
}
