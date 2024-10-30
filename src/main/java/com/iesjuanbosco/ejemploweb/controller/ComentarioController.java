package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/productos/view/{idProducto}/comentarios/add")
    public String AddComentario(@ModelAttribute Comentario comentario,
                                @PathVariable Long idProducto) {
        comentarioService.addComentario(idProducto, comentario);
        return "redirect:/productos/view/" + idProducto;
    }
}
