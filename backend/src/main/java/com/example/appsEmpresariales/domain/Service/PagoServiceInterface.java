package com.example.appsEmpresariales.domain.Service;

import com.example.appsEmpresariales.domain.dto.PagoDTO;

import java.util.List;

public interface PagoServiceInterface {

    // Crear
    PagoDTO guardarPago(PagoDTO pago);

    // Obtener por ID
    PagoDTO obtenerPagoPorId(String id);

    // Obtener todos
    List<PagoDTO> obtenerTodosLosPagos();

    // Actualizar
    PagoDTO actualizarPago(PagoDTO pago);

    // Eliminar
    void eliminarPago(String id);

    // Obtener por estado
    List<PagoDTO> obtenerPagosPorEstado(String estado);
}
