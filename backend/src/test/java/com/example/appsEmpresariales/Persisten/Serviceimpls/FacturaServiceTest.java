package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.FacturaEntity;
import com.example.appsEmpresariales.Persisten.Mapper.FacturaMapper;
import com.example.appsEmpresariales.domain.Repository.FacturaRepository;
import com.example.appsEmpresariales.domain.dto.FacturaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private FacturaMapper facturaMapper;

    @InjectMocks
    private FacturaService facturaService;

    private FacturaDTO facturaDTO;
    private FacturaEntity facturaEntity;

    @BeforeEach
    void setUp() {
        facturaDTO = new FacturaDTO();
        facturaDTO.setId("1");
        facturaDTO.setNumeroFactura("F001");

        facturaEntity = new FacturaEntity();
        facturaEntity.setId("1");
        facturaEntity.setNumeroFactura("F001");
    }

    // --- Crear ---
    @Test
    void testSave() {
        when(facturaMapper.toEntity(facturaDTO)).thenReturn(facturaEntity);
        when(facturaRepository.save(facturaEntity)).thenReturn(facturaEntity);
        when(facturaMapper.toDto(facturaEntity)).thenReturn(facturaDTO);

        FacturaDTO resultado = facturaService.save(facturaDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        verify(facturaRepository).save(facturaEntity);
    }

    // --- Leer por ID ---
    @Test
    void testFindById_Found() {
        when(facturaRepository.findById("1")).thenReturn(Optional.of(facturaEntity));
        when(facturaMapper.toDto(facturaEntity)).thenReturn(facturaDTO);

        FacturaDTO resultado = facturaService.findById("1");

        assertNotNull(resultado);
        assertEquals("F001", resultado.getNumeroFactura());
        verify(facturaRepository).findById("1");
    }

    @Test
    void testFindById_NotFound() {
        when(facturaRepository.findById("1")).thenReturn(Optional.empty());

        FacturaDTO resultado = facturaService.findById("1");

        assertNull(resultado);
        verify(facturaRepository).findById("1");
    }

    // --- Leer todas ---
    @Test
    void testFindAll() {
        when(facturaRepository.findAll()).thenReturn(List.of(facturaEntity));
        when(facturaMapper.toDto(facturaEntity)).thenReturn(facturaDTO);

        List<FacturaDTO> resultado = facturaService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("F001", resultado.get(0).getNumeroFactura());
        verify(facturaRepository).findAll();
    }

    // --- Actualizar ---
    @Test
    void testUpdate_WhenExists() {
        when(facturaRepository.existsById("1")).thenReturn(true);
        when(facturaMapper.toEntity(facturaDTO)).thenReturn(facturaEntity);
        when(facturaRepository.save(facturaEntity)).thenReturn(facturaEntity);
        when(facturaMapper.toDto(facturaEntity)).thenReturn(facturaDTO);

        FacturaDTO resultado = facturaService.update(facturaDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        verify(facturaRepository).save(facturaEntity);
    }

    @Test
    void testUpdate_WhenNotExists() {
        when(facturaRepository.existsById("1")).thenReturn(false);

        FacturaDTO resultado = facturaService.update(facturaDTO);

        assertNull(resultado);
        verify(facturaRepository, never()).save(any());
    }

    // --- Eliminar ---
    @Test
    void testDeleteById() {
        doNothing().when(facturaRepository).deleteById("1");

        facturaService.deleteById("1");

        verify(facturaRepository).deleteById("1");
    }

    // --- Buscar por número de factura ---
    @Test
    void testFindByNumeroFactura_Found() {
        when(facturaRepository.findByNumeroFactura("F001")).thenReturn(facturaEntity);
        when(facturaMapper.toDto(facturaEntity)).thenReturn(facturaDTO);

        FacturaDTO resultado = facturaService.findByNumeroFactura("F001");

        assertNotNull(resultado);
        assertEquals("F001", resultado.getNumeroFactura());
        verify(facturaRepository).findByNumeroFactura("F001");
    }

    @Test
    void testFindByNumeroFactura_NotFound() {
        when(facturaRepository.findByNumeroFactura("F001")).thenReturn(null);

        FacturaDTO resultado = facturaService.findByNumeroFactura("F001");

        assertNull(resultado);
        verify(facturaRepository).findByNumeroFactura("F001");
    }
}
