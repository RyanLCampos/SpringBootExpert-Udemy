package com.github.springudemy.libraryapi.controller;

import com.github.springudemy.libraryapi.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        if(authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }
        return "Olá " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code){
        return "Seu authorization code: " + code;
    }

}