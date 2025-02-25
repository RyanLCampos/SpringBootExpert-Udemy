package com.github.springudemy.libraryapi.controller.dto;

import java.time.LocalDate;

import com.github.springudemy.libraryapi.model.Autor;

public record AutorDTO(String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

    
    public Autor mapearParaAutor(){
        Autor autor = new Autor();

        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
