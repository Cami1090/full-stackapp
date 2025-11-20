package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.PagoEntity;
import com.example.appsEmpresariales.domain.Repository.PagoRepository;
import com.example.appsEmpresariales.domain.dto.PagoDTO;
import com.example.appsEmpresariales.Persisten.Mapper.PagoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoService pagoService;

    private PagoDTO pagoDTO;
    private PagoEntity pagoEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pagoDTO = new PagoDTO();
        pagoDTO.setId("1");
        pagoDTO.setEstadoPago("Completado");

        pagoEntity = new PagoEntity();
        pagoEntity.setId("1");
        pagoEntity.setEstadoPago("Completado");
    }

    @Test
    void testGuardarPago() {
        when(pagoMapper.toEntity(pagoDTO)).thenReturn(pagoEntity);
        when(pagoRepository.save(pagoEntity)).thenReturn(pagoEntity);
        when(pagoMapper.toDto(pagoEntity)).thenReturn(pagoDTO);

        PagoDTO resultado = pagoService.guardarPago(pagoDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        verify(pagoRepository).save(pagoEntity);
    }

    @Test
    void testObtenerPagoPorId() {
        when(pagoRepository.findById("1")).thenReturn(Optional.of(pagoEntity));
        when(pagoMapper.toDto(pagoEntity)).thenReturn(pagoDTO);

        PagoDTO resultado = pagoService.obtenerPagoPorId("1");

        assertNotNull(resultado);
        assertEquals("Completado", resultado.getEstadoPago());
    }

    @Test
    void testObtenerPagoPorIdNoExiste() {
        when(pagoRepository.findById("1")).thenReturn(Optional.empty());
        PagoDTO resultado = pagoService.obtenerPagoPorId("1");
        assertNull(resultado);
    }

    @Test
    void testObtenerTodosLosPagos() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoEntity));
        when(pagoMapper.toDto(pagoEntity)).thenReturn(pagoDTO);

        List<PagoDTO> lista = pagoService.obtenerTodosLosPagos();

        assertEquals(1, lista.size());
        verify(pagoRepository).findAll();
    }

    @Test
    void testEliminarPago() {
        doNothing().when(pagoRepository).deleteById("1");
        pagoService.eliminarPago("1");
        verify(pagoRepository).deleteById("1");
    }

    @Test
    void testActualizarPagoExiste() {
        when(pagoRepository.existsById("1")).thenReturn(true);
        when(pagoMapper.toEntity(pagoDTO)).thenReturn(pagoEntity);
        when(pagoRepository.save(pagoEntity)).thenReturn(pagoEntity);
        when(pagoMapper.toDto(pagoEntity)).thenReturn(pagoDTO);

        PagoDTO resultado = pagoService.actualizarPago(pagoDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        verify(pagoRepository).save(pagoEntity);
    }

    @Test
    void testActualizarPagoNoExiste() {
        when(pagoRepository.existsById("1")).thenReturn(false);
        PagoDTO resultado = pagoService.actualizarPago(pagoDTO);
        assertNull(resultado);
        verify(pagoRepository, never()).save(any());
    }

    @Test
    void testObtenerPagosPorEstado() {
        when(pagoRepository.findByEstadoPago("Completado")).thenReturn(List.of(pagoEntity));
        when(pagoMapper.toDto(pagoEntity)).thenReturn(pagoDTO);

        List<PagoDTO> lista = pagoService.obtenerPagosPorEstado("Completado");

        assertEquals(1, lista.size());
        assertEquals("Completado", lista.get(0).getEstadoPago());
    }
}
