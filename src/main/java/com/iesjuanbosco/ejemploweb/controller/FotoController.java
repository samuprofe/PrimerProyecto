package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.FotoProducto;
import com.iesjuanbosco.ejemploweb.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class FotoController {

    @Autowired
    FotoRepository fotoRepository;

    @GetMapping("productos/{id1}/fotos/{id2}/delete")
    public String deleteFoto(@PathVariable("id1") Long idProducto,
                             @PathVariable("id2") Long idFoto) {
        Optional<FotoProducto> fotoProducto = fotoRepository.findById(idFoto);
        if(fotoProducto.isPresent()){
            Path archivoFoto = Paths.get(fotoProducto.get().getNombre());
            try {
                Files.deleteIfExists(archivoFoto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fotoRepository.deleteById(idFoto);

        return "redirect:/productos/edit/" + idProducto ;
    }

}
