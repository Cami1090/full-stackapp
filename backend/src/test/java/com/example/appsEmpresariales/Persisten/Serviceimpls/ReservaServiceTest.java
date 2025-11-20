package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.ReservaEntity;
import com.example.appsEmpresariales.Persisten.Mapper.ReservaMapper;
import com.example.appsEmpresariales.domain.Repository.ReservaRepository;
import com.example.appsEmpresariales.domain.dto.ReservaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;
///
    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;

    private ReservaEntity reservaEntity;
    private ReservaDTO reservaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reservaEntity = new ReservaEntity();
        reservaEntity.setId("1");
        reservaEntity.setUsuario("juan");
        reservaEntity.setEstado("Confirmada");
        reservaEntity.setRecurso("Proyector");
        reservaEntity.setFechaInicio(LocalDate.of(2025, 1, 10));

        reservaDTO = new ReservaDTO();
        reservaDTO.setId("1");
        reservaDTO.setUsuario("juan");
        reservaDTO.setEstado("Confirmada");
        reservaDTO.setRecurso("Proyector");
        reservaDTO.setFechaInicio(LocalDate.of(2025, 1, 10));
    }

    // -------- CRUD --------

    @Test
    void testGuardarReserva() {
        when(reservaMapper.toEntity(reservaDTO)).thenReturn(reservaEntity);
        when(reservaRepository.save(reservaEntity)).thenReturn(reservaEntity);
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        ReservaDTO resultado = reservaService.guardarReserva(reservaDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        verify(reservaRepository, times(1)).save(reservaEntity);
    }

    @Test
    void testObtenerReservaPorId() {
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reservaEntity));
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        ReservaDTO resultado = reservaService.obtenerReservaPorId("1");

        assertNotNull(resultado);
        assertEquals("juan", resultado.getUsuario());
    }

    @Test
    void testObtenerTodasLasReservas() {
        when(reservaRepository.findAll()).thenReturn(List.of(reservaEntity));
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        List<ReservaDTO> lista = reservaService.obtenerTodasLasReservas();

        assertEquals(1, lista.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void testEliminarReserva() {
        doNothing().when(reservaRepository).deleteById("1");

        reservaService.eliminarReserva("1");

        verify(reservaRepository, times(1)).deleteById("1");
    }

    @Test
    void testActualizarReservaExiste() {
        when(reservaRepository.existsById("1")).thenReturn(true);
        when(reservaMapper.toEntity(reservaDTO)).thenReturn(reservaEntity);
        when(reservaRepository.save(reservaEntity)).thenReturn(reservaEntity);
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        ReservaDTO resultado = reservaService.actualizarReserva(reservaDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
    }

    @Test
    void testActualizarReservaNoExiste() {
        when(reservaRepository.existsById("1")).thenReturn(false);

        ReservaDTO resultado = reservaService.actualizarReserva(reservaDTO);

        assertNull(resultado);
        verify(reservaRepository, never()).save(any());
    }

    // -------- Búsquedas --------

    @Test
    void testObtenerReservasPorUsuario() {
        when(reservaRepository.findByUsuario("juan")).thenReturn(List.of(reservaEntity));
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        List<ReservaDTO> lista = reservaService.obtenerReservasPorUsuario("juan");

        assertEquals(1, lista.size());
        assertEquals("juan", lista.get(0).getUsuario());
    }

    @Test
    void testObtenerReservasPorEstado() {
        when(reservaRepository.findByEstado("Confirmada")).thenReturn(List.of(reservaEntity));
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        List<ReservaDTO> lista = reservaService.obtenerReservasPorEstado("Confirmada");

        assertEquals(1, lista.size());
        assertEquals("Confirmada", lista.get(0).getEstado());
    }

    @Test
    void testObtenerReservasPorRecurso() {
        when(reservaRepository.findByRecurso("Proyector")).thenReturn(List.of(reservaEntity));
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        List<ReservaDTO> lista = reservaService.obtenerReservasPorRecurso("Proyector");

        assertEquals(1, lista.size());
        assertEquals("Proyector", lista.get(0).getRecurso());
    }

    @Test
    void testObtenerReservasPorRangoFechas() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = LocalDate.of(2025, 1, 31);

        when(reservaRepository.findByFechaInicioBetween(inicio, fin)).thenReturn(List.of(reservaEntity));
        when(reservaMapper.toDto(reservaEntity)).thenReturn(reservaDTO);

        List<ReservaDTO> lista = reservaService.obtenerReservasPorRangoFechas(inicio, fin);

        assertEquals(1, lista.size());
        assertEquals(LocalDate.of(2025, 1, 10), lista.get(0).getFechaInicio());
    }

    // -------- Utilitarios --------

    @Test
    void testExisteReserva() {
        when(reservaRepository.existsById("1")).thenReturn(true);

        boolean existe = reservaService.existeReserva("1");

        assertTrue(existe);
        verify(reservaRepository, times(1)).existsById("1");
    }

    @Test
    void testContarReservas() {
        when(reservaRepository.count()).thenReturn(5L);

        long total = reservaService.contarReservas();

        assertEquals(5L, total);
        verify(reservaRepository, times(1)).count();
    }

    @Test
    void testContarReservasPorEstado() {
        when(reservaRepository.countByEstado("Confirmada")).thenReturn(3L);

        long total = reservaService.contarReservasPorEstado("Confirmada");

        assertEquals(3L, total);
        verify(reservaRepository, times(1)).countByEstado("Confirmada");
    }
}
