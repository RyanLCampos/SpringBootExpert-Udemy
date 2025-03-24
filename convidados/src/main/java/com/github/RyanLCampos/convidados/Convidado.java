package com.github.RyanLCampos.convidados;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Convidado {
    
    @Id
    @Column
    private String cpf;

    @Column
    private String nome;

}
