package com.example.appsEmpresariales.domain.Repository;

import com.example.appsEmpresariales.Persisten.Entity.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<RecursoEntity, String> {
    List<RecursoEntity> findByTipo(String tipo);
    List<RecursoEntity> findByEstado(String estado);
}
