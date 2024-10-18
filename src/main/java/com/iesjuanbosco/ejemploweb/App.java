package com.iesjuanbosco.ejemploweb;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;

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

		Categoria categoria = Categoria
				.builder()
				.nombre("Móviles")
				.descripcion("Los mejores móviles")
				.productos(new ArrayList<Producto>())
				.build();
		Producto p1 = Producto.builder()
				.cantidad(50)
				.precio(500.0)
				.titulo("Producto 1")
				.categoria(categoria)
				.build();
		Producto p2 = Producto.builder()
				.cantidad(100)
				.precio(200.0)
				.titulo("Producto 2")
				.categoria(categoria)
				.build();

		//Añado los productos a la categoria
		//categoria.setProductos(new ArrayList<Producto>());
		categoria.getProductos().add(p1);
		categoria.getProductos().add(p2);

		//Como la relación tiene CASCADE.ALL se guardan en cascada y guarda los productos de la categoría
		categoriaRepository.save(categoria);



	}

}
