package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Usuario;
import com.iesjuanbosco.ejemploweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findUsuarioByEmail(String email)
    {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Usuarnio no encontrado"));
    }
}
