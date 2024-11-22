package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.entity.Usuario;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import com.iesjuanbosco.ejemploweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void addComentario(Long idProducto, Comentario comentario, Principal principal) {
        comentario.setFecha(LocalDate.now());
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        comentario.setProducto(producto);
        //Obtengo el usuario de la BD
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        //Asocio el usuario al comentario
        comentario.setUsuario(usuario);
        comentarioRepository.save(comentario);
    }

    public void deleteComentario(Long idComentario, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Comentario comentario = comentarioRepository.findById(idComentario).orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        if(comentario.getUsuario().getId().equals(usuario.getId())) {
            comentarioRepository.delete(comentario);
        }else{
            throw new IllegalArgumentException("El usuario no es el propietario de este comentario");
        }
    }


}