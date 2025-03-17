package com.github.springudemy.libraryapi.controller;

import com.github.springudemy.libraryapi.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Login", description = "Operações relacionadas ao login e autenticação")
public class LoginViewController {

    @GetMapping("/login")
    @Operation(summary = "Página de Login", description = "Retorna a página de login para o usuário.")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    @Operation(summary = "Página Inicial", description = "Exibe uma saudação personalizada para o usuário autenticado.")
    public String paginaHome(Authentication authentication){
        if(authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }
        return "Olá " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Código de Autorização", description = "Recebe e retorna o código de autorização enviado na URL.")
    public String getAuthorizationCode(@RequestParam("code") String code){
        return "Seu authorization code: " + code;
    }

}