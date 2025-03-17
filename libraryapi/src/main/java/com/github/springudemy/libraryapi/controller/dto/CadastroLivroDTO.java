package com.github.springudemy.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.ISBN;

import com.github.springudemy.libraryapi.model.GeneroLivro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Schema(name = "Livro")
public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        @Schema(name = "isbn")
        String isbn,
        @NotBlank(message = "campo obrigatório")
        @Size(max = 150, message = "campo fora do tamanho padrão")
        @Schema(name = "titulo")
        String titulo,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser data futura")
        @Schema(name = "dataPublicacao")
        LocalDate dataPublicacao,
        @Schema(name = "genero")
        GeneroLivro genero,
        @Schema(name = "preco")
        BigDecimal preco,
        @NotNull(message = "campo obrigatório")
        @Schema(name = "idAutor")
        UUID idAutor
) {

}
