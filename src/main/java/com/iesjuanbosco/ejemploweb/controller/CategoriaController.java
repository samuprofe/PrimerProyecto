package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping ("/categorias")
    public String categoria(Model model) {
        //Con esto obtendríamos todas las categorías
        //List<Categoria> categorias  = categoriaRepository.findAll();
        //Con esto hacemos una consulta personalizada para obtener el coste medio y número de productos por categoria
        List<CategoriaCosteMedioDTO> categoriasConStats = categoriaRepository.obtenerCategoriasConStats();
        model.addAttribute("categorias", categoriasConStats);
        return "categoria-list";
    }

    @GetMapping("/categoria/delete/{id}")
    public String borrarCategoria(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";  // Redirige de nuevo a la lista de categorías
    }
}
