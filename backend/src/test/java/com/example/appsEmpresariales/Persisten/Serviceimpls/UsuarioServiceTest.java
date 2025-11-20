package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.UsuarioEntity;
import com.example.appsEmpresariales.Persisten.Mapper.UsuarioMapper;
import com.example.appsEmpresariales.domain.Repository.UsuarioRepository;
import com.example.appsEmpresariales.domain.dto.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    // ---------- TEST: obtenerTodosLosUsuarios ----------
    @Test
    void testObtenerTodosLosUsuarios() {
        UsuarioEntity e1 = new UsuarioEntity("1", "Ana", "Gómez", "ana@mail.com", "1234", "USER");
        UsuarioEntity e2 = new UsuarioEntity("2", "Luis", "Pérez", "luis@mail.com", "abcd", "ADMIN");
        List<UsuarioEntity> entities = Arrays.asList(e1, e2);

        when(usuarioRepository.findAll()).thenReturn(entities);

        try (MockedStatic<UsuarioMapper> mocked = Mockito.mockStatic(UsuarioMapper.class)) {
            mocked.when(() -> UsuarioMapper.toDTO(e1)).thenReturn(new UsuarioDTO("1", "Ana", "Gómez", "ana@mail.com", "1234", "USER"));
            mocked.when(() -> UsuarioMapper.toDTO(e2)).thenReturn(new UsuarioDTO("2", "Luis", "Pérez", "luis@mail.com", "abcd", "ADMIN"));

            List<UsuarioDTO> result = usuarioService.obtenerTodosLosUsuarios();

            assertEquals(2, result.size());
            assertEquals("Ana", result.get(0).getNombre());
        }
    }

    // ---------- TEST: obtenerUsuarioPorId ----------
    @Test
    void testObtenerUsuarioPorIdExiste() {
        UsuarioEntity entity = new UsuarioEntity("1", "Carlos", "Martínez", "carlos@mail.com", "pass", "USER");
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(entity));

        try (MockedStatic<UsuarioMapper> mocked = Mockito.mockStatic(UsuarioMapper.class)) {
            mocked.when(() -> UsuarioMapper.toDTO(entity))
                    .thenReturn(new UsuarioDTO("1", "Carlos", "Martínez", "carlos@mail.com", "pass", "USER"));

            UsuarioDTO result = usuarioService.obtenerUsuarioPorId("1");

            assertNotNull(result);
            assertEquals("Carlos", result.getNombre());
        }
    }

    // ---------- TEST: guardarUsuario ----------
    @Test
    void testGuardarUsuarioNuevo() {
        UsuarioDTO dto = new UsuarioDTO("3", "Laura", "López", "laura@mail.com", "mypass", "USER");
        UsuarioEntity entity = new UsuarioEntity("3", "Laura", "López", "laura@mail.com", "mypass", "USER");

        when(usuarioRepository.existsByEmail("laura@mail.com")).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(entity);

        try (MockedStatic<UsuarioMapper> mocked = Mockito.mockStatic(UsuarioMapper.class)) {
            mocked.when(() -> UsuarioMapper.toEntity(dto)).thenReturn(entity);
            mocked.when(() -> UsuarioMapper.toDTO(entity)).thenReturn(dto);

            UsuarioDTO result = usuarioService.guardarUsuario(dto);

            assertNotNull(result);
            assertEquals("Laura", result.getNombre());
            assertEquals("USER", result.getRol());
        }
    }

    // ---------- TEST: actualizarUsuario ----------
    @Test
    void testActualizarUsuarioExiste() {
        UsuarioDTO dto = new UsuarioDTO("4", "Miguel", "Reyes", "miguel@mail.com", "keypass", "ADMIN");
        UsuarioEntity entity = new UsuarioEntity("4", "Miguel", "Reyes", "miguel@mail.com", "keypass", "ADMIN");

        when(usuarioRepository.existsById("4")).thenReturn(true);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(entity);

        try (MockedStatic<UsuarioMapper> mocked = Mockito.mockStatic(UsuarioMapper.class)) {
            mocked.when(() -> UsuarioMapper.toEntity(dto)).thenReturn(entity);
            mocked.when(() -> UsuarioMapper.toDTO(entity)).thenReturn(dto);

            UsuarioDTO result = usuarioService.actualizarUsuario(dto);

            assertNotNull(result);
            assertEquals("Miguel", result.getNombre());
        }
    }

    // ---------- TEST: eliminarUsuario ----------
    @Test
    void testEliminarUsuarioExiste() {
        when(usuarioRepository.existsById("5")).thenReturn(true);

        usuarioService.eliminarUsuario("5");

        verify(usuarioRepository, times(1)).deleteById("5");
    }

    // ---------- TEST: obtenerUsuariosPorRol ----------
    @Test
    void testObtenerUsuariosPorRol() {
        UsuarioEntity e1 = new UsuarioEntity("6", "Mario", "Díaz", "mario@mail.com", "qwerty", "USER");
        List<UsuarioEntity> entities = List.of(e1);

        when(usuarioRepository.findByRol("USER")).thenReturn(entities);

        try (MockedStatic<UsuarioMapper> mocked = Mockito.mockStatic(UsuarioMapper.class)) {
            mocked.when(() -> UsuarioMapper.toDTO(e1))
                    .thenReturn(new UsuarioDTO("6", "Mario", "Díaz", "mario@mail.com", "qwerty", "USER"));

            List<UsuarioDTO> result = usuarioService.obtenerUsuariosPorRol("USER");

            assertEquals(1, result.size());
            assertEquals("Mario", result.get(0).getNombre());
        }
    }

    // ---------- TEST: existeUsuarioPorEmail ----------
    @Test
    void testExisteUsuarioPorEmail() {
        when(usuarioRepository.existsByEmail("user@mail.com")).thenReturn(true);

        boolean exists = usuarioService.existeUsuarioPorEmail("user@mail.com");

        assertTrue(exists);
    }
}
