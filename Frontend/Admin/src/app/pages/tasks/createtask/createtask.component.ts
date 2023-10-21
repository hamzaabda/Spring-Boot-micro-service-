import { Component, OnInit } from '@angular/core';
import { EventService } from './event.service';
import { CustomEvent } from './event.model';
import Swal from 'sweetalert2'
@Component({
  selector: 'app-create-task',
  templateUrl: './createtask.component.html',
})
export class CreatetaskComponent implements OnInit {
  eventData: CustomEvent = new CustomEvent(); // Initialisez eventData

  constructor(private eventService: EventService) {}

  // Ajoutez la méthode ngOnInit
  ngOnInit(): void {
    // Vous pouvez ajouter du code d'initialisation ici si nécessaire
  }

  // Créez une fonction pour soumettre un événement
  submitEvent(eventData: CustomEvent): void {
    this.eventService.createEvent(eventData).subscribe((event) => {
      console.log('Événement créé avec succès:', event);
      // Vous pouvez ajouter des actions supplémentaires ici
      Swal.fire({
        icon: 'success',
        title: 'Événement créé avec succès',
        text: 'Événement créé avec succès!',
        footer: '<a href="/tasks/list">Liste</a>'
      })
      
    });
  }
}
