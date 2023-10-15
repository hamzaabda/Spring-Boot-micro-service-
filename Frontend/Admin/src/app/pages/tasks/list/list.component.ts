import { Component, OnInit } from '@angular/core';
import { CustomEvent } from './event.model';
import { EventService } from './event.service';

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
}
