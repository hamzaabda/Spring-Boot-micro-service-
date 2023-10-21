// event.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomEvent } from './event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8085/api/evenement/events';

  constructor(private http: HttpClient) {}

  getAllEvents(): Observable<CustomEvent[]> {
    return this.http.get<CustomEvent[]>(`${this.baseUrl}/afficher`);
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getMonthlyStatistics(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/statistics/monthly`);
  }

  generateCSVReport(): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/generate-csv-report`, {
      responseType: 'blob' // Spécifie que la réponse est un blob (fichier)
    });
  }
  filterEventsByDateRange(startDate: string, endDate: string): Observable<CustomEvent[]> {
    // Créer des paramètres pour passer les dates au service
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);

    // Appeler le service avec les paramètres de date
    return this.http.get<CustomEvent[]>(`${this.baseUrl}/filter-by-date`, { params });
  }
  updateEvent(id: number, updatedEvent: CustomEvent): Observable<CustomEvent> {
    return this.http.put<CustomEvent>(`${this.baseUrl}/${id}`, updatedEvent);
  }
}
