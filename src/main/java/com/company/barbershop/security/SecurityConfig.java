package com.company.barbershop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}test123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                // ...
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home/index").permitAll()
                        .requestMatchers("/home/nosotros-info").permitAll()
                        .requestMatchers("/home/contacto-info").permitAll()
                        .requestMatchers("/barber-shop/turnos").permitAll()
                        .requestMatchers("/barber-shop/procesar-formulario-turno").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(form -> form.loginPage("/mostrarLogIn")
                        .loginProcessingUrl("/authenticateAdmin")
                        .permitAll());

        // Deshabilitar CSRF (si es necesario para tu aplicación)
//        http.csrf().disable();

        return http.build();
    }

}
