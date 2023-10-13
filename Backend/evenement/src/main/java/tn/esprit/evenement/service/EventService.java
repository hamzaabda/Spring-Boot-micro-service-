package tn.esprit.evenement.service;


import com.opencsv.CSVWriter;
import tn.esprit.evenement.entite.Event;
import tn.esprit.evenement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public EventService(EventRepository eventRepository, EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.entityManager = entityManager;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Événement non trouvé avec l'ID : " + id));
    }

    public Event createEvent(Event event) {
        // Vous pouvez ajouter ici une logique de validation ou de prétraitement si nécessaire
        return eventRepository.save(event);
    }

    public Event updateEvent(int id, Event updatedEvent) {
        Event existingEvent = getEventById(id);

        // Mettez à jour les propriétés de l'événement existant avec les nouvelles valeurs de updatedEvent
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setStartDate(updatedEvent.getStartDate());
        existingEvent.setEndDate(updatedEvent.getEndDate());

        // Vous pouvez ajouter ici une logique de validation ou de prétraitement si nécessaire

        // Enregistrez les modifications dans le repository
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(int id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }
    public List<Event> filterAndSearchEvents(String name, String location, Date startDate, Date endDate, String resource) {
        List<Event> filteredEvents = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            filteredEvents.addAll(eventRepository.findByNameContaining(name));
        }

        if (location != null && !location.isEmpty() && startDate != null && endDate != null) {
            filteredEvents.addAll(eventRepository.findByLocationAndStartDateGreaterThanEqualAndEndDateLessThanEqual(location, startDate, endDate));
        }

        if (resource != null && !resource.isEmpty()) {
            filteredEvents.addAll(eventRepository.findByResourcesContaining(resource));
        }

        return filteredEvents;
    }


    public List<Event> filterAndSearchEventsByDateRange(Date startDate, Date endDate) {
        return eventRepository.findByStartDateBetween(startDate, endDate);
    }


    public List<Object[]> getEventCountByMonth() {
        return eventRepository.countEventsByMonth();
    }

    public long getEventCount() {
        return eventRepository.count();
    }
    public Object getEventCountAndAverage() {
        String sql = "SELECT COUNT(*), AVG(id) FROM Event";
        Query query = entityManager.createNativeQuery(sql);

        Object[] result = (Object[]) query.getSingleResult();

        long eventCount = ((Number) result[0]).longValue();
        double eventAverage = ((Number) result[1]).doubleValue();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("eventCount", eventCount);
        resultMap.put("eventAverage", eventAverage);

        return resultMap;
    }




    public byte[] generateCSVReport() {
        try {
            // Récupérer les données pour le rapport depuis la base de données
            List<Event> events = eventRepository.findAll();

            // Créer un StringWriter pour écrire les données CSV
            StringWriter writer = new StringWriter();

            // Utiliser OpenCSV pour écrire les données dans le fichier CSV
            CSVWriter csvWriter = new CSVWriter(writer);
            String[] header = {"ID", "Nom", "Description", "Date de début", "Date de fin", "Lieu", "Ressources"};
            csvWriter.writeNext(header);

            for (Event event : events) {
                String[] data = {
                        String.valueOf(event.getId()),
                        event.getName(),
                        event.getDescription(),
                        event.getStartDate().toString(),
                        event.getEndDate().toString(),
                        event.getLocation(),
                        event.getResources()
                };
                csvWriter.writeNext(data);
            }

            // Fermer le CSVWriter
            csvWriter.close();

            // Obtenir le rapport au format CSV sous forme de chaîne
            String csvData = writer.toString();

            // Convertir la chaîne en tableau de bytes
            byte[] csvBytes = csvData.getBytes();

            return csvBytes;
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace();
            return new byte[0]; // Retourner un tableau vide en cas d'erreur
        }

    }
    public List<Event> detectAnomalies() {
        List<Event> events = eventRepository.findAll();
        // Utilisez les données des événements pour détecter des anomalies
        // en fonction des caractéristiques des dates

        // Retournez une liste d'événements anormaux
        return events.stream().filter(this::isAnomalous).collect(Collectors.toList());
    }

    private boolean isAnomalous(Event event) {
        // Implémentez votre logique de détection d'anomalies en fonction
        // des caractéristiques de dates des événements
        return event.getStartDate().after(event.getEndDate());
    }





}