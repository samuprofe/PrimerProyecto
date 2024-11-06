package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotNull (message ="Debes seleccionar una categoría")
    private Categoria categoria;

    @OneToMany(targetEntity = Comentario.class, cascade =CascadeType.ALL,
    mappedBy = "producto")
    private List<Comentario> comentarios = new ArrayList<Comentario>(); //Obligatorio inicializarlo

    @OneToMany(targetEntity = FotoProducto.class, cascade = CascadeType.ALL,
    mappedBy = "producto")
    private List<FotoProducto> fotos = new ArrayList<>();

}
