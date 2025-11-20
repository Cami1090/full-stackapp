import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecursoService {

  private api = 'http://localhost:8080/api/recursos';

  constructor(private http: HttpClient) {}

  getAllRecursos(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getRecursoById(id: string): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }

  crearRecurso(recurso: any): Observable<any> {
    return this.http.post(this.api, recurso);
  }

  actualizarRecurso(id: string, recurso: any): Observable<any> {
    return this.http.put(`${this.api}/${id}`, recurso);
  }

  eliminarRecurso(id: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}

