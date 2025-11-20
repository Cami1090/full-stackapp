package com.example.appsEmpresariales.Persisten.Serviceimpls;

import com.example.appsEmpresariales.Persisten.Entity.UsuarioEntity;
import com.example.appsEmpresariales.Persisten.Mapper.UsuarioMapper;
import com.example.appsEmpresariales.domain.Repository.UsuarioRepository;
import com.example.appsEmpresariales.domain.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // -------- CRUD --------

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioPorId(String id) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.map(UsuarioMapper::toDTO).orElse(null);
    }

    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {
        // Verificamos que el email no exista
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }

        UsuarioEntity entity = UsuarioMapper.toEntity(usuarioDTO);
        UsuarioEntity guardado = usuarioRepository.save(entity);
        return UsuarioMapper.toDTO(guardado);
    }

    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) {
        Optional<UsuarioEntity> usuarioExistenteOpt = usuarioRepository.findById(usuarioDTO.getId());
        if (usuarioExistenteOpt.isEmpty()) {
            return null; // Not found
        }

        UsuarioEntity usuarioExistente = usuarioExistenteOpt.get();
        
        // Verificar si el email está siendo cambiado y si ya existe
        if (!usuarioExistente.getEmail().equals(usuarioDTO.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("El email ya está registrado.");
            }
        }

        // Actualizar campos
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        
        // Solo actualizar contraseña si se proporciona una nueva
        if (usuarioDTO.getContraseña() != null && !usuarioDTO.getContraseña().trim().isEmpty()) {
            usuarioExistente.setContraseña(usuarioDTO.getContraseña());
        }
        
        // El rol no se actualiza desde el perfil (solo admin puede cambiar roles)
        // usuarioExistente.setRol(usuarioDTO.getRol());

        UsuarioEntity actualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(actualizado);
    }

    public void eliminarUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // -------- Consultas personalizadas --------

    public List<UsuarioDTO> obtenerUsuariosPorRol(String rol) {
        return usuarioRepository.findByRol(rol)
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // -------- Autenticación --------

    public UsuarioDTO autenticarUsuario(String email, String contraseña) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByEmailAndContraseña(email, contraseña);
        return usuarioOpt.map(UsuarioMapper::toDTO).orElse(null);
    }
}
