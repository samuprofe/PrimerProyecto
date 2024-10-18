package com.iesjuanbosco.ejemploweb.repository;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT c.nombre AS nombreCategoria, AVG(p.precio) AS costeMedio, COUNT(*) AS numeroProductos " +
            "FROM categorias c LEFT JOIN productos p ON c.id = p.id_categoria " +
            "GROUP BY c.id", nativeQuery = true)
    List<CategoriaCosteMedioDTO> obtenerCategoriasConStats();
}

