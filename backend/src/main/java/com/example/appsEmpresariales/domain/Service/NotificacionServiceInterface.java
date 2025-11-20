package com.example.appsEmpresariales.domain.Service;

import com.example.appsEmpresariales.domain.dto.NotificacionDTO;

import java.util.List;

public interface NotificacionServiceInterface {

    // Crear
    NotificacionDTO guardarNotificacion(NotificacionDTO notificacion);

    // Obtener por usuario
    List<NotificacionDTO> obtenerNotificacionesPorUsuario(String idUsuario);

    // Obtener por tipo
    List<NotificacionDTO> obtenerNotificacionesPorTipo(String tipo);

    // Obtener todas
    List<NotificacionDTO> obtenerTodas();

    // Eliminar
    void eliminarNotificacion(String id);
}

