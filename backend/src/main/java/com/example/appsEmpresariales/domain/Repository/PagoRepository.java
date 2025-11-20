package com.example.appsEmpresariales.domain.Repository;

import com.example.appsEmpresariales.Persisten.Entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, String> {
    List<PagoEntity> findByEstadoPago(String estadoPago);
}
