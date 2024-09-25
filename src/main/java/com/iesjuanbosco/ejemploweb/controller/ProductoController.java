package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ProductoController(ProductoRepository repository){
        this.productoRepository = repository;
    }

    @GetMapping("/productos2")    //Anotación que indica la URL localhost:8080/productos2 mediante GET
    @ResponseBody       //Anotación que indica que no pase por el motor de plantillas thymeleaf sino que voy a devolver yo el HTML diréctamente
    public String index(){
        List<Producto> productos = this.productoRepository.findAll();
        StringBuilder HTML = new StringBuilder("<html><body>");
        productos.forEach(producto -> {
            HTML.append("<p>" + producto.getTitulo() + "</p>");
        });
        HTML.append("</body></html>");

        return HTML.toString();
    }

    /* Con la anotación GetMapping le indicamos a Spring que el siguiente método
       se va a ejecutar cuando el usuario acceda a la URL http://localhost/productos */
    @GetMapping("/productos")
    public String findAll(Model model){
        List<Producto> productos = this.productoRepository.findAll();

        //Pasamos los datos a la vista
        model.addAttribute("productos",productos);
        model.addAttribute("titulo","Titulo de página");

        return "producto-list";
    }

    @GetMapping("/productos/add")
    public String add(){
        List<Producto> productos = new ArrayList<Producto>();
        Producto p1 = new Producto(null, "Producto 1", 20, 45.5);
        Producto p2 = new Producto(null, "Producto 2", 50, 5.0);
        Producto p3 = new Producto(null, "Producto 3", 30, 50.5);
        Producto p4 = new Producto(null, "Producto 4", 10, 30.0);
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);

        //Guardamos todos los productos en la base de datos utilizando el objeto productoRepository
        this.productoRepository.saveAll(productos);

        //Redirige al controlador /productos
        return "redirect:/productos";
    }

    //Borra un producto a partir del id de la ruta
    @GetMapping("/productos/del/{id}")
    public String delete(@PathVariable Long id){
        //Borrar el producto usando el repositorio
        productoRepository.deleteById(id);
        //Redirigir al listado de productos: /productos
        return "redirect:/productos";
    }

    //Muestra un producto a partir del id de la ruta
    @GetMapping("/productos/view/{id}")
    public String view(@PathVariable Long id, Model model){
        //Obtenemos el producto de la BD a partir del id de la barra de direcciones
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            //Mandamos el producto a la vista
            model.addAttribute("producto",producto.get());
            return "producto-view";
        }
        else{
            return "redirect:/productos";
        }
    }

    @GetMapping("/productos/new")
    public String newProducto(Model model){
        model.addAttribute("producto", new Producto());
        return "producto-new";
    }

    @PostMapping("/productos/new")
    public String newProductoInsert(@Valid Producto producto, BindingResult bindingResult){
        //Si ha habido errores de validación volvemos a mostrar el formulario
        if(bindingResult.hasErrors()){
            return "producto-new";
        }
        //Si no ha habido errores de validación insertamos los datos en la BD
        productoRepository.save(producto);
        //Redirigimos a /productos
        return "redirect:/productos";
    }

    @GetMapping("/productos/edit/{id}")
    public String newProducto(@PathVariable Long id, Model model){
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            //Pasamos el objeto a la vista
            model.addAttribute("producto",producto.get());
            return "producto-edit";
        }

        return "redirect:/productos";
    }

    @PostMapping("/productos/edit/{id}")
    public String newProductoUpdate(@PathVariable Long id,
                                    Producto producto){
        producto.setId(id);
        productoRepository.save(producto);
        return "redirect:/productos";
    }
}
