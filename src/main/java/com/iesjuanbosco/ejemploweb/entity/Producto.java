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

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public Producto() {
    }

    public Producto(Long id, String titulo, Integer cantidad, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Long id() {
        return id;
    }

    public Producto setId(Long id) {
        this.id = id;
        return this;
    }

    public String titulo() {
        return titulo;
    }

    public Producto setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Integer cantidad() {
        return cantidad;
    }

    public Producto setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public Double precio() {
        return precio;
    }

    public Producto setPrecio(Double precio) {
        this.precio = precio;
        return this;
    }

    public Categoria categoria() {
        return categoria;
    }

    public Producto setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }
}
