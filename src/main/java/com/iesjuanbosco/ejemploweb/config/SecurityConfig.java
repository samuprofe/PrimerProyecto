package com.iesjuanbosco.ejemploweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration      //Especifica que es una clase de configuración
@EnableWebSecurity  //Especificamos con esta clase vamos a configurar la seguridad de la App
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/productos").permitAll()
                        .requestMatchers("/productos/view/*").permitAll()
                        .requestMatchers("/categorias/new").hasRole("ADMIN")
                        .anyRequest().authenticated()   /* Por ejemplo para la URL "/productos/new" habría que estar autenticado con cualquier ROL */
                )
                .formLogin(
                        form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/productos")
                        .failureUrl("/productos?errorLogin")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para el logout
                        .logoutSuccessUrl("/productos") // URL de redirección después de logout
                        .invalidateHttpSession(true) // Invalida la sesión
                        .deleteCookies("JSESSIONID") // Borra las cookies de sesión
                        .permitAll()
                ) ;


        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails userDetails2 = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails, userDetails2);
    }

}
