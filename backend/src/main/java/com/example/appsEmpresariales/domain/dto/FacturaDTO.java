package com.example.appsEmpresariales.domain.dto;

import java.time.LocalDate;

public class FacturaDTO {
    private String id;
    private String numeroFactura;
    private LocalDate fechaEmision;
    private double total;
    private String idPago;

    public FacturaDTO() {}

    public FacturaDTO(String id, String numeroFactura, LocalDate fechaEmision, double total, String idPago) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.idPago = idPago;
    }

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

