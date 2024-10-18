package com.iesjuanbosco.ejemploweb;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

	//Comentario de ejemplo
	public static void main(String[] args) {

		ApplicationContext contex = SpringApplication.run(App.class, args);

		/*Así podemos acceder a un Bean (un respositorio, un controlador, una entidad, etc.) de Spring
		para, por ejemplo, insertar datos de ejemplo nada más ejecutar la app
		*/


		ProductoRepository productoRepository =  contex.getBean(ProductoRepository.class);
		CategoriaRepository categoriaRepository = contex.getBean(CategoriaRepository.class);

		Producto p1 =new Producto(null, "titulo",10,50.5);
		Producto p2 =new Producto(null, "titulo2",15,70.5);
		Categoria c = new Categoria(null, "Moviles", "Los mejores móviles");

		//Añado el producto 1 a la categoría Móviles
		p1.setCategoria(c);
		c.getProductos().add(p1);

		//Añado el producto 2 a la categoría Móviles
		p2.setCategoria(c);
		c.getProductos().add(p2);

		//Como la relación tiene CASCADE.ALL se guardan en cascada y guarda los productos de la categoría
		categoriaRepository.save(c);



	}

}
