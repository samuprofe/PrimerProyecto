package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping ("/categorias/")
    public String categoria(Model model) {
        List<CategoriaCosteMedioDTO> categorias = categoriaRepository.obtenerCategoriasConStats();
        model.addAttribute("categorias", categorias);
        return "categoria-list";
    }
}
