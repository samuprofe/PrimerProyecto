package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Para que Spring sepa que esta clase es un controlador tenemos que añadir la anotación @Controller antes de la clase
@Controller
public class ProductoController {

    //Para acceder al repositorio creamos una propiedad y la asignamos en el constructor
    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;
    private ComentarioRepository comentarioRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ComentarioRepository comentarioRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.comentarioRepository = comentarioRepository;
    }

    /* Con la anotación GetMapping le indicamos a Spring que el siguiente método
       se va a ejecutar cuando el usuario acceda a la URL http://localhost/productos */
    @GetMapping("/productos")
    public String findAll(Model model){

        List<Producto> productos = this.productoRepository.findAll();
        List<Categoria> categorias = this.categoriaRepository.findAll();
        //Pasamos los datos a la vista
        model.addAttribute("productos",productos);
        model.addAttribute("categorias",categorias);
        model.addAttribute("titulo","Titulo de página");

        return "producto-list";
    }

    @GetMapping("/productos/categoria/{id}")
    public String findAll(Model model, @PathVariable Long id) {

        if(id.equals(-1L)){
            return "redirect:/productos";
        }
        Optional<Categoria> categoriaSeleccionada = categoriaRepository.findById(id);
        if (categoriaSeleccionada.isPresent()) {
            List<Producto> productos = this.productoRepository.findByCategoria(categoriaSeleccionada.get());
            List<Categoria> categorias = this.categoriaRepository.findAll();
            //Pasamos todos los datos necesarios a la vista
            //Pasamos el id de la categoría seleccionada, lo paso para que thymeleaf me ponga el selected="selected"
            //en la categoría que estamos y aparezca seleccionada en el desplegable
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("productos", productos);
            model.addAttribute("categorias", categorias);

            return "producto-list";
        } else {
            //Categoría seleccionada no existe, redirijo a listado de productos
            return "redirect:/productos";
        }
    }


    //Borra un producto a partir del id de la ruta
    @GetMapping("/productos/del/{id}")
    public String delete(@PathVariable Long id){
        //Borrar el producto usando el repositorio
        productoRepository.deleteById(id);
        //Redirigir al listado de getProductos: /getProductos
        return "redirect:/productos";
    }

    //Muestra un producto a partir del id de la ruta
    @GetMapping("/productos/view/{id}")
    public String view(@PathVariable Long id, Model model){
        //Obtenemos el producto de la BD a partir del id de la barra de direcciones
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            List <Comentario> comentarios = comentarioRepository.findByProductoOrderByFechaDesc(producto.get());
            //Mandamos el producto y los comentarios a la vista
            model.addAttribute("producto",producto.get());
            model.addAttribute("comentarios",comentarios);
            model.addAttribute("comentario", new Comentario());
            return "producto-view";
        }
        else{
            return "redirect:/productos";
        }
    }

    @GetMapping("/productos/new")
    public String newProducto(Model model){

        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto-new";
    }

    @PostMapping("/productos/new")
    public String newProductoInsert(Model model, @Valid Producto producto, BindingResult bindingResult){
        //Si ha habido errores de validación volvemos a mostrar el formulario
        if(bindingResult.hasErrors()){
            Sort sort = Sort.by("nombre").ascending();
            model.addAttribute("categorias", categoriaRepository.findAll(sort));
            return "producto-new";
        }

        //Si no ha habido errores de validación insertamos los datos en la BD
        productoRepository.save(producto);

        //Redirigimos a /getProductos
        return "redirect:/productos";
    }

    @GetMapping("/productos/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model){
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            //Pasamos el objeto a la vista
            model.addAttribute("producto",producto.get());
            return "producto-edit";
        }

        return "redirect:/productos";
    }

    @PostMapping("/productos/edit/{id}")
    public String editProductoUpdate(@PathVariable Long id,
                                    Producto producto){
        producto.setId(id);
        productoRepository.save(producto);
        return "redirect:/productos";
    }
}
