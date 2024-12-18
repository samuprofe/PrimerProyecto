package com.iesjuanbosco.ejemploweb.controller;

import ch.qos.logback.core.model.Model;
import com.iesjuanbosco.ejemploweb.entity.FotoProducto;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.FotoProductoRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import com.iesjuanbosco.ejemploweb.service.FotoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class FotoProductoController {

    @Autowired
    private FotoProductoRepository fotoProductoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FotoProductoService fotoProductoService;

    @GetMapping("productos/{id1}/fotos/{id2}/delete")
    public String deleteFoto(@PathVariable("id1") Long idProducto,
                             @PathVariable("id2") Long idFoto) {
        fotoProductoService.deleteFoto(idFoto);
        return "redirect:/productos/edit/" + idProducto ;
    }


    @PostMapping("/productos/edit/{id}/addfoto")
    public String addFoto(@PathVariable("id") Long idFoto, Model model,
                          @RequestParam(value = "archivoFoto") MultipartFile archivoFoto) {
        Optional<Producto> productoOptional = productoRepository.findById(idFoto);
        if(productoOptional.isPresent()){
            fotoProductoService.addFoto(archivoFoto, productoOptional.get());
        }
        return "redirect:/productos/edit/" + idFoto;
    }
}
