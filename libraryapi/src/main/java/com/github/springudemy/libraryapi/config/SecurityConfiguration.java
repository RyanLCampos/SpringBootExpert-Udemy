package com.github.springudemy.libraryapi.config;

import com.github.springudemy.libraryapi.security.CustomUserDetailsService;
import com.github.springudemy.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                    authorize.requestMatchers("/login/**").permitAll(); // Roles: Todos

                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll(); // Roles: Todos

                    authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // Roles: ADMIN

                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN"); // Roles: ADMIN, USER

                    authorize.anyRequest().authenticated(); // Qualquer requisição precisa estar autenticada
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){

//        UserDetails user1 = User.builder()
//                .username("usuario")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("123"))
//                .roles("ADMIN")
//                .build();


//        return new InMemoryUserDetailsManager(user1, user2);

        return new CustomUserDetailsService(usuarioService);
    }

}
