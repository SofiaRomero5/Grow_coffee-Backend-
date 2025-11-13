package com.grow_coffee.grow_coffee.Model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "insumos")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private double cantidad;

    @Column(nullable = false)
    private String unidad; // Ejemplo: kg, litros, unidades

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    // Añadimos precio (nullable si quieres)
    @Column(nullable = true)
    private Double precio;

    public Inventario() {}

    public Inventario(String nombre, double cantidad, String unidad, LocalDate fechaVencimiento, Double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.fechaVencimiento = fechaVencimiento;
        this.precio = precio;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
