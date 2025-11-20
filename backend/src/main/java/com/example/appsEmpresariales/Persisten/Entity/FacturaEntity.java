package com.example.appsEmpresariales.Persisten.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facturas")
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String numeroFactura;

    @Column(nullable = false)
    private LocalDate fechaEmision;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private String idPago;

    // -------- Constructores --------
    public FacturaEntity() {}

    public FacturaEntity(String id, String numeroFactura, LocalDate fechaEmision, double total, String idPago) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.idPago = idPago;
    }

    // -------- Getters & Setters --------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getIdPago() { return idPago; }
    public void setIdPago(String idPago) { this.idPago = idPago; }
}
