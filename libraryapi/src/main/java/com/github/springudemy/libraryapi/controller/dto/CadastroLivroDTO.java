package com.github.springudemy.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.github.springudemy.libraryapi.model.GeneroLivro;

public record CadastroLivroDTO(
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro generoLivro,
        BigDecimal preco,
        UUID idAutor
) {

}
