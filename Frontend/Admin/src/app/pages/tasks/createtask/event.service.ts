import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomEvent } from './event.model'; // Utilisez l'alias ici

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8085/api/evenement/events';

  constructor(private http: HttpClient) {}

  getAllEvents(): Observable<CustomEvent[]> {
    return this.http.get<CustomEvent[]>(`${this.baseUrl}`);
  }

  createEvent(event: CustomEvent): Observable<CustomEvent> {
    return this.http.post<CustomEvent>(`${this.baseUrl}/create`, event);
  }

  // Ajoutez les autres méthodes pour les opérations CRUD
}
