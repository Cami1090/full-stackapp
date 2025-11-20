import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PagoService {

  private api = 'http://localhost:8080/api/pagos';

  constructor(private http: HttpClient) {}

  crearPago(pago: any): Observable<any> {
    return this.http.post(this.api, pago);
  }

  getAllPagos(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getPagoById(id: string): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }
}

