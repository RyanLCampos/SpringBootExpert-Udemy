package com.github.springudemy.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll(); // Pagina customizada
                }) // Configuração de login padrão
                .httpBasic(Customizer.withDefaults()) // Configuração de autenticação básica padrão
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated(); // Qualquer requisição precisa estar autenticada
                })
                .build();
    }

}
