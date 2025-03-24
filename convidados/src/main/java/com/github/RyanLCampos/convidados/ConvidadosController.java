package com.github.RyanLCampos.convidados;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@CrossOrigin("*") // Permite que a API recebe requisições de outros dominios
@RequiredArgsConstructor
public class ConvidadosController {

    private final ConvidadosRepository repository;
    
    @GetMapping
    public List<Convidado> getConvidados(){
        return repository.findAll();
    }

}
