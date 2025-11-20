package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.RecursoEntity;
import com.example.appsEmpresariales.Persisten.Mapper.RecursoMapper;
import com.example.appsEmpresariales.domain.Repository.RecursoRepository;
import com.example.appsEmpresariales.domain.dto.RecursoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecursoServiceTest {

    @Mock
    private RecursoRepository recursoRepository;

    @Mock
    private RecursoMapper recursoMapper;

    @InjectMocks
    private RecursoService recursoService;

    private RecursoEntity recursoEntity;
    private RecursoDTO recursoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recursoEntity = new RecursoEntity();
        recursoEntity.setId("1");
        recursoEntity.setTipo("Computador");
        recursoEntity.setEstado("Disponible");

        recursoDTO = new RecursoDTO();
        recursoDTO.setId("1");
        recursoDTO.setTipo("Computador");
        recursoDTO.setEstado("Disponible");
    }

    @Test
    void testGuardarRecurso() {
        when(recursoMapper.toEntity(recursoDTO)).thenReturn(recursoEntity);
        when(recursoRepository.save(recursoEntity)).thenReturn(recursoEntity);
        when(recursoMapper.toDto(recursoEntity)).thenReturn(recursoDTO);

        RecursoDTO resultado = recursoService.guardarRecurso(recursoDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        verify(recursoRepository, times(1)).save(recursoEntity);
    }

    @Test
    void testObtenerRecursoPorId() {
        when(recursoRepository.findById("1")).thenReturn(Optional.of(recursoEntity));
        when(recursoMapper.toDto(recursoEntity)).thenReturn(recursoDTO);

        RecursoDTO resultado = recursoService.obtenerRecursoPorId("1");

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
    }

    @Test
    void testObtenerTodosLosRecursos() {
        when(recursoRepository.findAll()).thenReturn(List.of(recursoEntity));
        when(recursoMapper.toDto(recursoEntity)).thenReturn(recursoDTO);

        List<RecursoDTO> lista = recursoService.obtenerTodosLosRecursos();

        assertEquals(1, lista.size());
        verify(recursoRepository, times(1)).findAll();
    }

    @Test
    void testEliminarRecurso() {
        doNothing().when(recursoRepository).deleteById("1");

        recursoService.eliminarRecurso("1");

        verify(recursoRepository, times(1)).deleteById("1");
    }

    @Test
    void testActualizarRecurso() {
        when(recursoRepository.existsById("1")).thenReturn(true);
        when(recursoMapper.toEntity(recursoDTO)).thenReturn(recursoEntity);
        when(recursoRepository.save(recursoEntity)).thenReturn(recursoEntity);
        when(recursoMapper.toDto(recursoEntity)).thenReturn(recursoDTO);

        RecursoDTO resultado = recursoService.actualizarRecurso(recursoDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
    }

    @Test
    void testActualizarRecursoNoExiste() {
        when(recursoRepository.existsById("1")).thenReturn(false);

        RecursoDTO resultado = recursoService.actualizarRecurso(recursoDTO);

        assertNull(resultado);
        verify(recursoRepository, never()).save(any());
    }

    @Test
    void testObtenerRecursosPorTipo() {
        when(recursoRepository.findByTipo("Computador")).thenReturn(List.of(recursoEntity));
        when(recursoMapper.toDto(recursoEntity)).thenReturn(recursoDTO);

        List<RecursoDTO> lista = recursoService.obtenerRecursosPorTipo("Computador");

        assertEquals(1, lista.size());
        assertEquals("Computador", lista.get(0).getTipo());
    }

    @Test
    void testObtenerRecursosPorEstado() {
        when(recursoRepository.findByEstado("Disponible")).thenReturn(List.of(recursoEntity));
        when(recursoMapper.toDto(recursoEntity)).thenReturn(recursoDTO);

        List<RecursoDTO> lista = recursoService.obtenerRecursosPorEstado("Disponible");

        assertEquals(1, lista.size());
        assertEquals("Disponible", lista.get(0).getEstado());
    }
}
