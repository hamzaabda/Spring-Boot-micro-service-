package tn.esprit.reclamation.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.reclamation.entity.EventFeedback;
import tn.esprit.reclamation.repository.EventFeedbackRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventFeedbackService {
    private final EventFeedbackRepository repository;

    @Autowired
    public EventFeedbackService(EventFeedbackRepository repository) {
        this.repository = repository;
    }

    public List<EventFeedback> getAllFeedbacks() {
        return repository.findAll();
    }

    public Optional<EventFeedback> getFeedbackById(Long id) {
        return repository.findById(id);
    }

    public EventFeedback createFeedback(EventFeedback feedback) {
        return repository.save(feedback);
    }

    public EventFeedback updateFeedback(Long id, EventFeedback updatedFeedback) {
        if (repository.existsById(id)) {
            updatedFeedback.setId(id);
            return repository.save(updatedFeedback);
        }
        return null; // Handle error as needed
    }

    public void deleteFeedback(Long id) {
        repository.deleteById(id);
    }
}
