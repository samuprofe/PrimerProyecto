package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity     //Especifica que esta clase es una entidad
@Table(name="productos")    //Incida que la tabla en la base de datos relacionada con esta entidad
public class Producto {

    @Id     //Esta anotación especifica que este campo va a ser la clave principal de la tabla en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Esta anotación especifica que la clave primaria sea "auto-increment"
    private Long id;
    @NotEmpty (message = "El título no puede estar en blanco")
    private String titulo;
    @NotNull (message = "La cantidad no puede estar en blanco")
    private Integer cantidad;
    @NotNull (message = "El precio no puede estar en blanco")
    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precio;

    public Producto(Long id, String titulo, Integer cantidad, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Producto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
