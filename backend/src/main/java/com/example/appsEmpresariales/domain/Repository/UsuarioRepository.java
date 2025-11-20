package com.example.appsEmpresariales.domain.Repository;

import com.example.appsEmpresariales.Persisten.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {

    // Buscar por email
    Optional<UsuarioEntity> findByEmail(String email);

    // Buscar por rol
    List<UsuarioEntity> findByRol(String rol);

    // Saber si existe un email
    boolean existsByEmail(String email);

    // Buscar por email y contraseña
    Optional<UsuarioEntity> findByEmailAndContraseña(String email, String contraseña);
}
