package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.ReservaEntity;
import com.example.appsEmpresariales.domain.Repository.ReservaRepository;
import com.example.appsEmpresariales.domain.dto.ReservaDTO;
import com.example.appsEmpresariales.Persisten.Mapper.ReservaMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    // -------- CRUD --------
    public ReservaDTO guardarReserva(ReservaDTO reserva) {
        ReservaEntity entity = reservaMapper.toEntity(reserva);
        ReservaEntity guardado = reservaRepository.save(entity);
        return reservaMapper.toDto(guardado);
    }

    public ReservaDTO obtenerReservaPorId(String id) {
        return reservaRepository.findById(id)
                .map(reservaMapper::toDto)
                .orElse(null);
    }

    public List<ReservaDTO> obtenerTodasLasReservas() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public void eliminarReserva(String id) {
        reservaRepository.deleteById(id);
    }

    public ReservaDTO actualizarReserva(ReservaDTO reserva) {
        if (reservaRepository.existsById(reserva.getId())) {
            ReservaEntity entity = reservaMapper.toEntity(reserva);
            ReservaEntity actualizado = reservaRepository.save(entity);
            return reservaMapper.toDto(actualizado);
        }
        return null;
    }

    // -------- Búsquedas --------
    public List<ReservaDTO> obtenerReservasPorUsuario(String usuario) {
        return reservaRepository.findByUsuario(usuario)
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> obtenerReservasPorEstado(String estado) {
        return reservaRepository.findByEstado(estado)
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> obtenerReservasPorRecurso(String recurso) {
        return reservaRepository.findByRecurso(recurso)
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> obtenerReservasPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return reservaRepository.findByFechaInicioBetween(inicio, fin)
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    // -------- Utilitarios --------
    public boolean existeReserva(String id) {
        return reservaRepository.existsById(id);
    }

    public long contarReservas() {
        return reservaRepository.count();
    }

    public long contarReservasPorEstado(String estado) {
        return reservaRepository.countByEstado(estado);
    }
}
