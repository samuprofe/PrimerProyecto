package com.iesjuanbosco.ejemploweb.repository;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    //Consulta JPQL
    @Query("SELECT new com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO(c.id, c.nombre, AVG(p.precio), count(p), c.foto) " +
            "FROM Categoria c LEFT JOIN c.productos p GROUP BY c.id")
    List<CategoriaCosteMedioDTO> obtenerCategoriasConStats();
}

