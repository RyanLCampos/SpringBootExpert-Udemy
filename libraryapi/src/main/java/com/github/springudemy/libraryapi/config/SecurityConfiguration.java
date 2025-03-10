package com.github.springudemy.libraryapi.config;

import com.github.springudemy.libraryapi.security.CustomUserDetailsService;
import com.github.springudemy.libraryapi.security.LoginSocialSuccessHandler;
import com.github.springudemy.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Permite utilizar regras de acesso nos Controllers
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll(); // Pagina customizada
                }) // Configuração de login padrão
                .httpBasic(Customizer.withDefaults()) // Configuração de autenticação básica padrão
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll(); // Roles: Todos

                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll(); // Roles: Todos

                    // authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // Roles: ADMIN

                    // authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN"); // Roles: ADMIN, USER

                    authorize.anyRequest().authenticated(); // Qualquer requisição precisa estar autenticada
                })
                .oauth2Login(oauth2 ->{

                    oauth2
                        .loginPage("/login")
                        .successHandler(successHandler);
                })
                .oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
                .build();
    }

    // CONFIGURA O PREFIXO ROLE
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }

    // CONFIGURA, NO TOKEN JWT, O PREFIXO SCOPE
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
}
