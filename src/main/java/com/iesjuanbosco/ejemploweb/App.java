package com.iesjuanbosco.ejemploweb;

import com.iesjuanbosco.ejemploweb.entity.Producto;
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
		var repository = contex.getBean(ProductoRepository.class);



		repository.save(new Producto(null, "titulo",10,50.5));
		repository.save(new Producto(null, "titulo 2",30,51.5));





	}

}
