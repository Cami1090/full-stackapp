package com.example.appsEmpresariales.Persisten.config;

import com.example.appsEmpresariales.Persisten.Entity.UsuarioEntity;
import com.example.appsEmpresariales.domain.Repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init() {
        // Verificar si ya existe el admin
        if (!usuarioRepository.existsByEmail("admin@example.com")) {
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNombre("Admin");
            admin.setApellido("Sistema");
            admin.setEmail("admin@example.com");
            admin.setContraseña("admin123");
            admin.setRol("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN creado: admin@example.com / admin123");
        }
    }
}

