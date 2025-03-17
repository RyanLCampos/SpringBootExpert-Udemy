package com.github.springudemy.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.github.springudemy.libraryapi.model.Autor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório") // Strings (Em branco e null)
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrão") // Define a quantidade de characteres na String
        @Schema(name = "nome")
        String nome,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrão")
        @Schema(name = "nacionalidade")
        String nacionalidade
) {

    
    public Autor mapearParaAutor(){
        Autor autor = new Autor();

        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
