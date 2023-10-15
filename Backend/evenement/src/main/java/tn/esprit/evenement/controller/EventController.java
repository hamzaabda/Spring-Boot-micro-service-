package tn.esprit.evenement.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import tn.esprit.evenement.entite.Event;
import tn.esprit.evenement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {


    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }
    @GetMapping("/afficher")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }



    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(id, updatedEvent);
    }
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
    }


    @GetMapping("/filter-and-search")
    public List<Event> filterAndSearchEvents(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(name = "resource", required = false) String resource) {
        return eventService.filterAndSearchEvents(name, location, startDate, endDate, resource);
    }


    @GetMapping("/filter-by-date")
    public List<Event> filterEventsByDateRange(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (startDate != null && endDate != null) {
            return eventService.filterAndSearchEventsByDateRange(startDate, endDate);
        } else {
            // Gérer le cas où les dates ne sont pas spécifiées
            return new ArrayList<>();
        }
    }



    @GetMapping("/getCount")
    public long getEventCount() {
        return eventService.getEventCount();
    }


    @GetMapping("/stats")
    public Object getEventStats() {
        return eventService.getEventCountAndAverage();
    }

    @GetMapping("/statistics/monthly")
    public ResponseEntity<List<Object[]>> getEventCountByMonth() {
        List<Object[]> statistics = eventService.getEventCountByMonth();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
    @GetMapping("/generate-csv-report")
    public ResponseEntity<Resource> generateCSVReport() {
        byte[] csvReport = eventService.generateCSVReport();

        // Créer un objet Resource à partir des données du fichier CSV
        ByteArrayResource resource = new ByteArrayResource(csvReport);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=event-report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }


    @GetMapping("/detectAnomalies")
    public ResponseEntity<List<Event>> detectAnomalies() {
        List<Event> anomalies = eventService.detectAnomalies();
        return ResponseEntity.ok(anomalies);
    }


}