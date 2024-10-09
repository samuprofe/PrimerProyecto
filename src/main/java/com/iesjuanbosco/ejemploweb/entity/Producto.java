package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity     //Especifica que esta clase es una entidad
@Table(name="productos")    //Incida que la tabla en la base de datos relacionada con esta entidad
public class Producto {

    @Id     //Esta anotación especifica que este campo va a ser la clave principal de la tabla en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Esta anotación especifica que la clave primaria sea "auto-increment"
    private Long id;
    @NotEmpty (message = "El título no puede estar en blanco")
    @Column(length = 1000)
    private String titulo;
    @NotNull (message = "La cantidad no puede estar en blanco")
    private Integer cantidad;
    @NotNull (message = "El precio no puede estar en blanco")
    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precio;

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(targetEntity = Comentario.class, cascade =CascadeType.ALL,
    mappedBy = "producto")
    private List<Comentario> comentario = new ArrayList<Comentario>();

    public Producto() {
    }

    public Producto(Long id, String titulo, Integer cantidad, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "El título no puede estar en blanco") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotEmpty(message = "El título no puede estar en blanco") String titulo) {
        this.titulo = titulo;
    }

    public @NotNull(message = "La cantidad no puede estar en blanco") Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(@NotNull(message = "La cantidad no puede estar en blanco") Integer cantidad) {
        this.cantidad = cantidad;
    }

    public @NotNull(message = "El precio no puede estar en blanco") @Min(value = 0, message = "El precio debe ser positivo") Double getPrecio() {
        return precio;
    }

    public void setPrecio(@NotNull(message = "El precio no puede estar en blanco") @Min(value = 0, message = "El precio debe ser positivo") Double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
