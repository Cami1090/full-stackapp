package com.example.appsEmpresariales.Persisten.Mapper;

import com.example.appsEmpresariales.Persisten.Entity.PagoEntity;
import com.example.appsEmpresariales.domain.dto.PagoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    PagoDTO toDto(PagoEntity entity);
    PagoEntity toEntity(PagoDTO dto);
}
