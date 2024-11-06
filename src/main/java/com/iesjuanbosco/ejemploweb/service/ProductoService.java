package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ComentarioRepository comentarioRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           ComentarioRepository comentarioRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    public List<Producto> findProductosByCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public void deleteProductoById(Long id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> findProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public List<Comentario> findComentariosByProducto(Producto producto) {
        return comentarioRepository.findByProductoOrderByFechaDesc(producto);
    }

    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public List<Categoria> findAllCategoriasSorted() {
        return categoriaRepository.findAll(Sort.by("nombre").ascending());
    }
}