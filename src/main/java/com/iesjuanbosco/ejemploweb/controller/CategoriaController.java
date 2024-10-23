package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/categorias/new")
    public String addCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria-new";
    }

    @PostMapping("/categorias/new")
    public String addCategoriaInsert(@ModelAttribute("categoria") Categoria categoria,
                                     Model model,
                                     @RequestAttribute ("file") MultipartFile file) {
        //Validaciones
        List<String> tiposPermitidos= List.of("image/jpeg", "image/png", "image/gif","image/avif","image/webp");
        /*
        if(file == null || file.isEmpty()) {

        }
        if(!tiposPermitidos.contains(file.getContentType()){

        }
        if(file.getSize()>10000000){

        }*/

        //Cambiamos el nombre del archivo
        UUID nombreUnico = UUID.randomUUID();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String nuevoNombreFoto = nombreUnico + extension;
        //Guardar el archivo en disco
        Path ruta = Paths.get("uploads"+ File.separator +nuevoNombreFoto);
        try {
            byte[] contenido = file.getBytes();
            Files.write(ruta, contenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Guardamos la ruta en la BD
        categoria.setFoto(nuevoNombreFoto);
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }
}
