package com.github.springudemy.libraryapi.security;

import com.github.springudemy.libraryapi.model.Usuario;
import com.github.springudemy.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService service;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if(authentication instanceof CustomAuthentication customAuthentication){
           return customAuthentication.getUsuario();
       }

       return null;
    }

}
