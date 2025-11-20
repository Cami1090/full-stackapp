package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.FacturaEntity;

import com.example.appsEmpresariales.domain.Repository.FacturaRepository;
import com.example.appsEmpresariales.domain.dto.FacturaDTO;
import com.example.appsEmpresariales.Persisten.Mapper.FacturaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final FacturaMapper facturaMapper;

    public FacturaService(FacturaRepository facturaRepository, FacturaMapper facturaMapper) {
        this.facturaRepository = facturaRepository;
        this.facturaMapper = facturaMapper;
    }

    // Crear
    public FacturaDTO save(FacturaDTO dto) {
        FacturaEntity entity = facturaMapper.toEntity(dto);
        FacturaEntity guardada = facturaRepository.save(entity);
        return facturaMapper.toDto(guardada);
    }

    // Leer por ID
    public FacturaDTO findById(String id) {
        return facturaRepository.findById(id)
                .map(facturaMapper::toDto)
                .orElse(null);
    }

    // Leer todas
    public List<FacturaDTO> findAll() {
        return facturaRepository.findAll()
                .stream()
                .map(facturaMapper::toDto)
                .collect(Collectors.toList());
    }

    // Actualizar
    public FacturaDTO update(FacturaDTO dto) {
        if (facturaRepository.existsById(dto.getId())) {
            FacturaEntity entity = facturaMapper.toEntity(dto);
            FacturaEntity actualizada = facturaRepository.save(entity);
            return facturaMapper.toDto(actualizada);
        }
        return null;
    }

    // Eliminar
    public void deleteById(String id) {
        facturaRepository.deleteById(id);
    }

    // Buscar por número de factura
    public FacturaDTO findByNumeroFactura(String numeroFactura) {
        FacturaEntity entity = facturaRepository.findByNumeroFactura(numeroFactura);
        return entity != null ? facturaMapper.toDto(entity) : null;
    }
}
