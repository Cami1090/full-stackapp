package com.example.appsEmpresariales.Persisten.Mapper;

import com.example.appsEmpresariales.Persisten.Entity.ReservaEntity;
import com.example.appsEmpresariales.domain.dto.ReservaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    ReservaDTO toDto(ReservaEntity entity);
    ReservaEntity toEntity(ReservaDTO dto);
}
