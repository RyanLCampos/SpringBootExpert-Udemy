package com.github.RyanLCampos.convidados;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin("*") // Permite que a API recebe requisições de outros dominios 
public class ConvidadosController {
    
    @GetMapping
    public List<Convidado> getConvidados(){
        List<Convidado> lista = new ArrayList<Convidado>();
        
        lista.add(new Convidado("Fulano", "10354865151"));
        lista.add(new Convidado("Ciclano", "54861325471"));

        return lista;
    }

}
