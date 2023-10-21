import { Component, OnInit } from '@angular/core';
import { CustomEvent } from './event.model';
import { EventService } from './event.service';
import Swal  from 'sweetalert2';
@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  events: CustomEvent[];
  monthlyStatistics: any[];
  startDate: string = '';
  endDate: string = '';
  // Ajoutez une variable pour l'événement à mettre à jour
  updatedEvent: CustomEvent = new CustomEvent();
  openUpdateForm(event: CustomEvent) {
    this.updatedEvent = { ...event }; // Copie de l'événement pour mise à jour
  }

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.loadEvents();
    this.loadMonthlyStatistics();
  }

  loadEvents() {
    this.eventService.getAllEvents().subscribe((events) => {
      this.events = events;
    });
  }

  loadMonthlyStatistics() {
    this.eventService.getMonthlyStatistics().subscribe((statistics) => {
      this.monthlyStatistics = statistics;
    });
  }

  deleteEvent(id: number) {
    this.eventService.deleteEvent(id).subscribe(() => {
      Swal.fire({
        icon: 'success',
        title: 'Événement supprimé',
        text: 'Événement supprimé avec succès!',
        footer: '<a href="/tasks/list">Liste</a>'
      })
      this.loadEvents();
    });
  }

  generateCSV() {
    this.eventService.generateCSVReport().subscribe((data) => {
      // Créez un lien pour le téléchargement du fichier
      const blob = new Blob([data], { type: 'text/csv' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'event-report.csv';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  filterByDate() {
    this.eventService.filterEventsByDateRange(this.startDate, this.endDate).subscribe((filteredEvents) => {
      this.events = filteredEvents;
    });
  }
 
  updateEvent(updatedEvent: CustomEvent) {
    this.eventService.updateEvent(updatedEvent.id, updatedEvent).subscribe((response) => {
      Swal.fire({
        icon: 'success',
        title: 'Événement mis à jour',
        text: 'Événement mis à jour avec succès!'
      });
      this.loadEvents();
      this.updatedEvent = new CustomEvent(); // Réinitialiser l'événement mis à jour
    });
  }
  
  
}
