package com.example.appsEmpresariales.Persisten.Mapper;

import com.example.appsEmpresariales.Persisten.Entity.RecursoEntity;
import com.example.appsEmpresariales.domain.dto.RecursoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecursoMapper {
    RecursoDTO toDto(RecursoEntity entity);
    RecursoEntity toEntity(RecursoDTO dto);
}
