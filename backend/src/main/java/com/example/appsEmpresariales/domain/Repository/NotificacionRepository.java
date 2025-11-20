package com.example.appsEmpresariales.domain.Repository;

import com.example.appsEmpresariales.Persisten.Entity.NotificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionEntity, String> {
    List<NotificacionEntity> findByIdUsuario(String idUsuario);
    List<NotificacionEntity> findByTipo(String tipo);
}
