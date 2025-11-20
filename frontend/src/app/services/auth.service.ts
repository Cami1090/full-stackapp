import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api = 'http://localhost:8080/api/usuarios';  // ← URL de tu amigo

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    return this.http.post<any>(`${this.api}/login`, {
      email: email,
      contraseña: password
    });
  }

  register(usuario: any): Observable<any> {
    return this.http.post(this.api, usuario);
  }

  getAllUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  actualizarUsuario(id: string, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.api}/${id}`, usuario);
  }

  getUsuarioById(id: string): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }
}
