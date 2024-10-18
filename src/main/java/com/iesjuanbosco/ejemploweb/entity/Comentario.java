package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//Anotaciones de LomBok
@Data   //Incluye @ToString, @Getter, @Setter, @RequiredArgsConstructor y @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder    //Patron Builder
//Anotacion de Spring Data JPA
@Entity
public class Comentario {

    //id, titulo, texto, fecha y producto relacionado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000)
    private String texto;
    private LocalDate fecha;

    @ManyToOne(targetEntity = Producto.class)
    private Producto producto;


}
