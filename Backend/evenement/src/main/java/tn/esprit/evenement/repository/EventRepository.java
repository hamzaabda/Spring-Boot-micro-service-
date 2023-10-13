package tn.esprit.evenement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.evenement.entite.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByLocationAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String location, Date startDate, Date endDate);
    List<Event> findByResourcesContaining(String resource);
    List<Event> findByNameContaining(String name);

    List<Event> findByStartDateBetween(Date startDate, Date endDate);

    @Query("SELECT MONTH(e.startDate) AS month, COUNT(e) AS eventCount FROM Event e GROUP BY month")
    List<Object[]> countEventsByMonth();


    // Requête personnalisée pour rechercher des événements dans une plage de dates
    @Query("SELECT e FROM Event e WHERE e.startDate >= :startDate AND e.startDate <= :endDate")
    List<Event> findEventsInDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



    @Query("SELECT COUNT(e) FROM Event e WHERE e.name = :eventName")
    Long countEventsByName(@Param("eventName") String eventName);

}