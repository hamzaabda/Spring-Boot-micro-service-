import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8085/api/evenement/events';

  constructor(private http: HttpClient) {}

  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.baseUrl}`);
  }

  createEvent(event: Event): Observable<Event> {
    return this.http.post<Event>(`${this.baseUrl}/create`, event);
  }

  // Ajoutez les autres méthodes pour les opérations CRUD
}
