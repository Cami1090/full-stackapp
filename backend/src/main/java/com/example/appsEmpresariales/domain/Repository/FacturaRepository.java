package com.example.appsEmpresariales.domain.Repository;

import com.example.appsEmpresariales.Persisten.Entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, String> {
    FacturaEntity findByNumeroFactura(String numeroFactura);
}
