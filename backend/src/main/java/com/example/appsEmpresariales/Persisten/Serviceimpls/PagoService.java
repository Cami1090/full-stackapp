package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.PagoEntity;
import com.example.appsEmpresariales.domain.Repository.PagoRepository;
import com.example.appsEmpresariales.domain.dto.PagoDTO;
import com.example.appsEmpresariales.Persisten.Mapper.PagoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    public PagoService(PagoRepository pagoRepository, PagoMapper pagoMapper) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
    }

    public PagoDTO guardarPago(PagoDTO pago) {
        PagoEntity entity = pagoMapper.toEntity(pago);
        PagoEntity guardado = pagoRepository.save(entity);
        return pagoMapper.toDto(guardado);
    }

    public PagoDTO obtenerPagoPorId(String id) {
        return pagoRepository.findById(id)
                .map(pagoMapper::toDto)
                .orElse(null);
    }

    public List<PagoDTO> obtenerTodosLosPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toDto)
                .collect(Collectors.toList());
    }

    public void eliminarPago(String id) {
        pagoRepository.deleteById(id);
    }

    public PagoDTO actualizarPago(PagoDTO pago) {
        if (pagoRepository.existsById(pago.getId())) {
            PagoEntity entity = pagoMapper.toEntity(pago);
            PagoEntity actualizado = pagoRepository.save(entity);
            return pagoMapper.toDto(actualizado);
        }
        return null;
    }

    public List<PagoDTO> obtenerPagosPorEstado(String estado) {
        return pagoRepository.findByEstadoPago(estado)
                .stream()
                .map(pagoMapper::toDto)
                .collect(Collectors.toList());
    }
}
