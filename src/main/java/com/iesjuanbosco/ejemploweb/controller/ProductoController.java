package com.iesjuanbosco.ejemploweb.controller;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import com.iesjuanbosco.ejemploweb.entity.*;
import com.iesjuanbosco.ejemploweb.repository.UsuarioRepository;
import com.iesjuanbosco.ejemploweb.service.FotoProductoService;
import com.iesjuanbosco.ejemploweb.service.ProductoService;
import com.iesjuanbosco.ejemploweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {

    private final ProductoService productoService;
    private final FotoProductoService fotoProductoService;
    private final UsuarioService usuarioService;

    @Autowired
    public ProductoController(ProductoService productoService, FotoProductoService fotoProductoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.fotoProductoService = fotoProductoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/productos")
    public String findAll(Model model) {
        model.addAttribute("productos", productoService.findAllProductos());
        model.addAttribute("categorias", productoService.findAllCategorias());
        model.addAttribute("titulo", "Titulo de página");
        return "producto-list";
    }

    @GetMapping("/productos/categoria/{id}")
    public String findAllByCategoria(Model model, @PathVariable Long id) {
        if (id.equals(-1L)) {
            return "redirect:/productos";
        }
        Optional<Categoria> categoriaSeleccionada = productoService.findCategoriaById(id);
        if (categoriaSeleccionada.isPresent()) {
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("productos", productoService.findProductosByCategoria(categoriaSeleccionada.get()));
            model.addAttribute("categorias", productoService.findAllCategorias());
            return "producto-list";
        }
        return "redirect:/productos";
    }

    @GetMapping("/productos/del/{id}")
    public String delete(@PathVariable Long id) {

        productoService.deleteProductoById(id);
        return "redirect:/productos";
    }

    @GetMapping("/productos/view/{id}")
    public String view(@PathVariable Long id,
                       Model model,
                       Principal principal,
                       RedirectAttributes redirectAttributes,
                       @AuthenticationPrincipal UserDetails userDetails //Si habéis extendido UserDetails ya estarán aquí todos los datos
    ) {
        try {
            Producto producto = productoService.findProductoById(id);
            Usuario usuario = usuarioService.findUsuarioByEmail(principal.getName());

            model.addAttribute("producto", producto);
            model.addAttribute("comentario", new Comentario());

            //Pasamos el usuario para que podamos pintar el icono "borrar" solo en sus comentarios
            model.addAttribute("usuarioConectado", usuario);

            return "producto-view";
        }catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/productos";
        }

    }

    @GetMapping("/productos/new")
    public String newProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", productoService.findAllCategorias());
        return "producto-new";
    }

    @PostMapping("/productos/new")
    public String newProductoInsert(Model model, @Valid Producto producto,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            return "producto-new";
        }

        //Guardar fotos
        try {
            fotoProductoService.guardarFotos(fotos, producto);
        }catch (IllegalArgumentException ex) {
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            model.addAttribute("mensaje", ex.getMessage());
            return "producto-new";
        }

        //Guardar producto
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model) {

        try {
            Producto producto = productoService.findProductoById(id);
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            model.addAttribute("producto", producto);
            return "producto-edit";
        }
        catch (IllegalArgumentException ex) {
            return "redirect:/productos";
        }


    }

    @PostMapping("/productos/edit/{id}")
    public String editProductoUpdate(@PathVariable Long id, Producto producto) {
        producto.setId(id);
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }
}
