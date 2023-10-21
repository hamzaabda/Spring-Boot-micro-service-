package tn.esprit.reclamation.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.reclamation.entity.EventFeedback;
import tn.esprit.reclamation.service.EventFeedbackService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
public class EventFeedbackController {
    private final EventFeedbackService service;

    @Autowired
    public EventFeedbackController(EventFeedbackService service) {
        this.service = service;
    }

    @GetMapping("/afficher")
    public List<EventFeedback> getAllFeedbacks() {
        return service.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    public Optional<EventFeedback> getFeedbackById(@PathVariable Long id) {
        return service.getFeedbackById(id);
    }


    @PostMapping("/create")
    public EventFeedback createFeedback(@RequestBody EventFeedback feedback) {
        return service.createFeedback(feedback);
    }

    @PutMapping("/{id}")
    public EventFeedback updateFeedback(@PathVariable Long id, @RequestBody EventFeedback updatedFeedback) {
        return service.updateFeedback(id, updatedFeedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        service.deleteFeedback(id);
    }
}
