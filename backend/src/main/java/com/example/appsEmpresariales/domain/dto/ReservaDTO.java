package com.example.appsEmpresariales.domain.dto;

import java.time.LocalDate;

public class ReservaDTO {
    private String id;
    private String usuario;     // Usuario que hace la reserva
    private String recurso;     // Aula, laboratorio u otro recurso
    private String estado;      // PENDIENTE, CONFIRMADA, CANCELADA
    private String asiento;     // Opcional: si aplica
    private String precio;      // Costo asociado (si aplica)
    private LocalDate fechaInicio;  // Inicio de la reserva
    private LocalDate fechaFin;     // Fin de la reserva

    public ReservaDTO() {}

    public ReservaDTO(String id, String usuario, String recurso,
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
