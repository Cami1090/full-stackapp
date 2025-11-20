package com.example.appsEmpresariales.domain.Service;

import com.example.appsEmpresariales.domain.dto.ReservaDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReservaServiceInterface {

    // -------- CRUD --------
    ReservaDTO guardarReserva(ReservaDTO reserva);

    ReservaDTO obtenerReservaPorId(String id);

    List<ReservaDTO> obtenerTodasLasReservas();

    void eliminarReserva(String id);

    ReservaDTO actualizarReserva(ReservaDTO reserva);

    // -------- Búsquedas --------
    List<ReservaDTO> obtenerReservasPorUsuario(String usuario);

    List<ReservaDTO> obtenerReservasPorEstado(String estado);

    List<ReservaDTO> obtenerReservasPorRecurso(String recurso);

    List<ReservaDTO> obtenerReservasPorRangoFechas(LocalDate inicio, LocalDate fin);

    // -------- Utilitarios --------
    boolean existeReserva(String id);

    long contarReservas();

    long contarReservasPorEstado(String estado);
}

