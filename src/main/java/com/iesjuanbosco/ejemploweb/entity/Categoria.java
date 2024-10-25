package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//Anotaciones de LomBok
@Data   //Incluye @ToString, @Getter, @Setter, @RequiredArgsConstructor y @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder    //Patron Builder
//Anotacion de Spring Data JPA
@Entity
@Table(name="categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(length = 1000)
    private String descripcion;
    private String foto;

    @OneToMany(targetEntity = Producto.class, cascade = CascadeType.ALL,
            mappedBy = "categoria")
    private List<Producto> productos = new ArrayList<>();

}
