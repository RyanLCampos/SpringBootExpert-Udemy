package com.github.spring_udemy.arquiteturaspring.montadora;

public class HondaHRV extends Carro{

    public HondaHRV(Motor motor) {
        super(motor);
        setModelo("HRV");
        setCor("Preto");
        setMontadora(Montadora.HONDA);
    }

}
