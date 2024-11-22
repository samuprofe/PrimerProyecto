package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/productos/view/{idProducto}/comentarios/add")
    public String AddComentario(@ModelAttribute Comentario comentario,
                                @PathVariable Long idProducto, Principal principal) {
        comentarioService.addComentario(idProducto, comentario, principal);
        return "redirect:/productos/view/" + idProducto;
    }

    @GetMapping("/productos/{idProducto}/comentarios/{idComentario}/delete")
    public String DeleteComentario(@PathVariable Long idProducto,
                                   @PathVariable Long idComentario,
                                   Principal principal,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        try {
            comentarioService.deleteComentario(idComentario, principal);
        }catch (IllegalArgumentException ex){
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/productos/view/" + idProducto;

    }
}
