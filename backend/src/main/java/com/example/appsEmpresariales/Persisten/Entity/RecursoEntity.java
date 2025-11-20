package com.example.appsEmpresariales.Persisten.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recursos")
public class RecursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(nullable = false)
    private int capacidad;

    @Column(nullable = false, length = 200)
    private String ubicacion;

    @Column(nullable = false, length = 50)
    private String estado;

    public RecursoEntity() {}

    public RecursoEntity(String id, String nombre, String tipo, int capacidad, String ubicacion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
