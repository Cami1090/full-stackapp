package com.example.appsEmpresariales.Persisten.Mapper;

import com.example.appsEmpresariales.Persisten.Entity.NotificacionEntity;
import com.example.appsEmpresariales.domain.dto.NotificacionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {
    NotificacionDTO toDto(NotificacionEntity entity);
    NotificacionEntity toEntity(NotificacionDTO dto);
}
