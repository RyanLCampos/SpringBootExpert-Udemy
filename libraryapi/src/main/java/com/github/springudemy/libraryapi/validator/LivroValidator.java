package com.github.springudemy.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.springudemy.libraryapi.exceptions.CampoInvalidoException;
import com.github.springudemy.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro){
        if(existeIsbnCadastrado(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "O campo 'preço' é obrigatório para livros com ano de publicação a partir de 2020.");
        }
    }

    private boolean existeIsbnCadastrado(Livro livro){

        Optional<Livro> optionalLivro = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){ // Quando estiver cadastrando livro
            return optionalLivro.isPresent();
        }

        // Quando estiver atualizando livro
        return optionalLivro
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }

    private boolean isPrecoObrigatorioNulo(Livro livro){

        // Verifica se preço é nulo e ano de publicação maior ou igual ao ANO_EXIGENCIA_PRECO (Padrão - 2020)
        return livro.getPreco() == null & livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

}
