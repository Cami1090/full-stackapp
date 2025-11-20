package com.example.appsEmpresariales.Persisten.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String usuario;   // Usuario que hace la reserva

    @Column(nullable = false)
    private String recurso;   // Aula, laboratorio u otro recurso

    @Column(nullable = false)
    private String estado;    // PENDIENTE, CONFIRMADA, CANCELADA

    private String asiento;   // Opcional

    private String precio;    // Costo asociado (si aplica)

    @Column(nullable = false)
    private LocalDate fechaInicio;  // Inicio de la reserva

    @Column(nullable = false)
    private LocalDate fechaFin;     // Fin de la reserva

    // -------- Constructores --------
    public ReservaEntity() {}

    public ReservaEntity(String id, String usuario, String recurso,
                         String estado, String asiento, String precio,
                         LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.usuario = usuario;
        this.recurso = recurso;
        this.estado = estado;
        this.asiento = asiento;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // -------- Getters & Setters --------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getRecurso() { return recurso; }
    public void setRecurso(String recurso) { this.recurso = recurso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getAsiento() { return asiento; }
    public void setAsiento(String asiento) { this.asiento = asiento; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}
