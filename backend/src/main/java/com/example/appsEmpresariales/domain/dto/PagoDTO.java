package com.example.appsEmpresariales.domain.dto;

import java.time.LocalDate;

public class PagoDTO {
    private String id;
    private double monto;
    private LocalDate fechaPago;
    private String metodoPago;
    private String estadoPago;
    private String idReserva;

    public PagoDTO() {}

    public PagoDTO(String id, double monto, LocalDate fechaPago, String metodoPago, String estadoPago, String idReserva) {
        this.id = id;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
        this.idReserva = idReserva;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public String getIdReserva() { return idReserva; }
    public void setIdReserva(String idReserva) { this.idReserva = idReserva; }
}
