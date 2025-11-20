package com.example.appsEmpresariales.domain.dto;

import java.time.LocalDate;

public class NotificacionDTO {
    private String id;
    private String mensaje;
    private String tipo;
    private LocalDate fechaEnvio;
    private String idUsuario;

    public NotificacionDTO() {}

    public NotificacionDTO(String id, String mensaje, String tipo, LocalDate fechaEnvio, String idUsuario) {
        this.id = id;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fechaEnvio = fechaEnvio;
        this.idUsuario = idUsuario;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
}
