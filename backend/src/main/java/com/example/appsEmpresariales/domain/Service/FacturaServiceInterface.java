package com.example.appsEmpresariales.domain.Service;

import com.example.appsEmpresariales.domain.dto.FacturaDTO;

import java.util.List;

public interface FacturaServiceInterface {

    // Crear
    FacturaDTO save(FacturaDTO facturaDTO);

    // Leer por ID
    FacturaDTO findById(String id);

    // Leer todas
    List<FacturaDTO> findAll();

    // Actualizar
    FacturaDTO update(FacturaDTO facturaDTO);

    // Eliminar
    void deleteById(String id);

    // Contar
    long count();

    // Buscar por número de factura
    FacturaDTO findByNumeroFactura(String numeroFactura);
}
