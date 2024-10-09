package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @OneToMany(targetEntity = Producto.class, cascade = CascadeType.ALL,
            mappedBy = "categoria")

    private List<Producto> productos = new ArrayList<>();

    public Categoria() {
    }

    public Categoria(Long id, String nombre, String descripcion, List productos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = productos;
    }

    public Long id() {
        return id;
    }

    public Categoria setId(Long id) {
        this.id = id;
        return this;
    }

    public String nombre() {
        return nombre;
    }

    public Categoria setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String descripcion() {
        return descripcion;
    }

    public Categoria setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public List getProductos() {
        return productos;
    }

    public Categoria setProductos(List productos) {
        this.productos = productos;
        return this;
    }
}
