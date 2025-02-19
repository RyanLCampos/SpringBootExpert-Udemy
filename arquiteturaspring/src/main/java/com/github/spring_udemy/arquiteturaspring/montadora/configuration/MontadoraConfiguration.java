package com.github.spring_udemy.arquiteturaspring.montadora.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.spring_udemy.arquiteturaspring.montadora.Motor;
import com.github.spring_udemy.arquiteturaspring.montadora.TipoMotor;

@Configuration
public class MontadoraConfiguration {

    @Bean(name = "motorAspirado")
    public Motor motorAspirado() {

        var motor = new Motor();

        motor.setCavalos(120);

        motor.setCilindros(4);

        motor.setModelo("XPTO-0");

        motor.setLitragem(2.0);

        motor.setTipoMotor(TipoMotor.ASPIRADO);

        return motor;
    }

    @Bean(name = "motorEletrico")
    public Motor motorEletrico() {

        var motor = new Motor();

        motor.setCavalos(110);

        motor.setCilindros(3);

        motor.setModelo("TH-10");

        motor.setLitragem(1.4);

        motor.setTipoMotor(TipoMotor.ELETRICO);

        return motor;
    }

    @Bean(name = "motorTurbo")
    @Primary
    public Motor motorTurbo() {

        var motor = new Motor();

        motor.setCavalos(180);

        motor.setCilindros(4);

        motor.setModelo("XPTO-01");

        motor.setLitragem(1.5);

        motor.setTipoMotor(TipoMotor.TURBO);

        return motor;
    }

}
