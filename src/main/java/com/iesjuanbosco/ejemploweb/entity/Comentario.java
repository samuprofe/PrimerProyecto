package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Comentario {

    //id, titulo, texto, fecha y producto relacionado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String titulo;
    @Column(length = 2000)
    private String texto;
    private LocalDate fecha;

    @ManyToOne(targetEntity = Producto.class)
    private Producto producto;

}
