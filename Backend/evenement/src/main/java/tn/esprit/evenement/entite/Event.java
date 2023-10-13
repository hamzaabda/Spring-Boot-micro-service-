package tn.esprit.evenement.entite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Date startDate; // Date de début de l'événement
    private Date endDate;   // Date de fin de l'événement
    private String location; // Lieu de l'événement
    private String resources; // Ressources nécessaires pour l'événement

    // Getters et setters
}
