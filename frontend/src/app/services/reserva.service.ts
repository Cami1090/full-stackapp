import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private api = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) {}

  getAllReservas(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getReservaById(id: string): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }

  crearReserva(reserva: any): Observable<any> {
    return this.http.post(this.api, reserva);
  }

  actualizarReserva(id: string, reserva: any): Observable<any> {
    return this.http.put(`${this.api}/${id}`, reserva);
  }

  eliminarReserva(id: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  getReservasPorUsuario(usuarioId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/usuario/${usuarioId}`);
  }

  getReservasPorEstado(estado: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/estado/${estado}`);
  }

  getReservasPorRecurso(recursoId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/recurso/${recursoId}`);
  }
}

