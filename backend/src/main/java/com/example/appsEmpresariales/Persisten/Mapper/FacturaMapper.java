package com.example.appsEmpresariales.Persisten.Mapper;

import com.example.appsEmpresariales.domain.dto.FacturaDTO;
import com.example.appsEmpresariales.Persisten.Entity.FacturaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    FacturaDTO toDto(FacturaEntity entity);
    FacturaEntity toEntity(FacturaDTO dto);
}
