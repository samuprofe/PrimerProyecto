package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public void addComentario(Long idProducto, Comentario comentario) {
        comentario.setFecha(LocalDate.now());
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        comentario.setProducto(producto);
        comentarioRepository.save(comentario);
    }
}