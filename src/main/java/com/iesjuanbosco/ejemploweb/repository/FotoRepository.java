package com.iesjuanbosco.ejemploweb.repository;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.FotoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository <FotoProducto, Long> {
}
