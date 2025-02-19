package com.github.spring_udemy.arquiteturaspring.montadora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.spring_udemy.arquiteturaspring.montadora.CarroStatus;
import com.github.spring_udemy.arquiteturaspring.montadora.Chave;
import com.github.spring_udemy.arquiteturaspring.montadora.HondaHRV;
import com.github.spring_udemy.arquiteturaspring.montadora.Motor;

@RestController
@RequestMapping("/carros")
public class TesteFabricaController {

    
    // @Qualifier("motorTurbo") Escolher qual dos beans da classe de configuração usar.
    @Autowired
    @Eletrico // Criou uma annotation para usar no lugar do Qualifier.
    private Motor motor;

    @PostMapping("/ligar")
    public CarroStatus ligarCarro(@RequestBody Chave chave){
        var carro = new HondaHRV(motor);

        return carro.darIgnicao(chave);
    }

}
