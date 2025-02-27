package com.github.springudemy.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.github.springudemy.libraryapi.model.GeneroLivro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        String isbn,
        @NotBlank(message = "campo obrigatório")
        @Size(max = 150, message = "campo fora do tamanho padrão")
        String titulo,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatório")
        UUID idAutor
) {

}
