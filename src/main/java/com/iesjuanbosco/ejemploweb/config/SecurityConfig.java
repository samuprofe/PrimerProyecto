package com.iesjuanbosco.ejemploweb.config;

import com.iesjuanbosco.ejemploweb.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      //Especifica que es una clase de configuración
@EnableWebSecurity  //Especificamos con esta clase vamos a configurar la seguridad de la App
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                    authorize
                                    .requestMatchers("/productos").permitAll()
                                    .requestMatchers("/productos/view/*").permitAll()
                                    .requestMatchers("/categorias/new").hasRole("ADMIN")
                                    .anyRequest().authenticated()   /* Por ejemplo para la URL "/productos/new" habría que estar autenticado con cualquier ROL */
                )
                .formLogin(
                        form -> form
                        .loginPage("/productos")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/productos")
                        .failureUrl("/productos?errorLogin")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para el logout
                        .logoutSuccessUrl("/productos") // URL de redirección después de logout
                        .permitAll()
                ) ;


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }



    /*
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
    *
     */

}
