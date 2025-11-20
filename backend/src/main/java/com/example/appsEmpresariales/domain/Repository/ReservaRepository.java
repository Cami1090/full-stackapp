package com.example.appsEmpresariales.domain.Repository;

import com.example.appsEmpresariales.Persisten.Entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, String> {
    List<ReservaEntity> findByUsuario(String usuario);
    List<ReservaEntity> findByEstado(String estado);
    List<ReservaEntity> findByRecurso(String recurso);
    List<ReservaEntity> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);
    long countByEstado(String estado);
}
