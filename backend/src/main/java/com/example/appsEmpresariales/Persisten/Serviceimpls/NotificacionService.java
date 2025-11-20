package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.NotificacionEntity;
import com.example.appsEmpresariales.domain.Repository.NotificacionRepository;
import com.example.appsEmpresariales.domain.dto.NotificacionDTO;
import com.example.appsEmpresariales.Persisten.Mapper.NotificacionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;

    public NotificacionService(NotificacionRepository notificacionRepository,
                               NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    public NotificacionDTO guardarNotificacion(NotificacionDTO notificacion) {
        NotificacionEntity entity = notificacionMapper.toEntity(notificacion);
        return notificacionMapper.toDto(notificacionRepository.save(entity));
    }

    public List<NotificacionDTO> obtenerNotificacionesPorUsuario(String idUsuario) {
        return notificacionRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNotificacionesPorTipo(String tipo) {
        return notificacionRepository.findByTipo(tipo)
                .stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerTodas() {
        return notificacionRepository.findAll()
                .stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    public void eliminarNotificacion(String id) {
        notificacionRepository.deleteById(id);
    }
}
