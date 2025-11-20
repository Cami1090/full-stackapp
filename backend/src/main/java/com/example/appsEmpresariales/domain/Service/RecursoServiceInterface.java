package com.example.appsEmpresariales.domain.Service;

import com.example.appsEmpresariales.domain.dto.RecursoDTO;

import java.util.List;

public interface RecursoServiceInterface {

    RecursoDTO guardarRecurso(RecursoDTO recurso);

    RecursoDTO obtenerRecursoPorId(String id);

    List<RecursoDTO> obtenerTodosLosRecursos();

    void eliminarRecurso(String id);

    RecursoDTO actualizarRecurso(RecursoDTO recurso);

    List<RecursoDTO> obtenerRecursosPorTipo(String tipo);

    List<RecursoDTO> obtenerRecursosPorEstado(String estado);
}
