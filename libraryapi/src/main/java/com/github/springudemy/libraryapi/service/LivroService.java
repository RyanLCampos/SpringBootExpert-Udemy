package com.github.springudemy.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.repository.LivroRepository;
import com.github.springudemy.libraryapi.validator.LivroValidator;

import static com.github.springudemy.libraryapi.repository.specs.LivroSpecs.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

        // select * from livro where isbn = :isbn and nomeAutor = ...
        // Specification<Livro> specs = Specification
        //         .where(LivroSpecs.isbnEqual(isbn))
        //         .and(LivroSpecs.tituloLike(titulo))
        //         .and(LivroSpecs.generoEqual(genero));

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro){
        if(livro.getId() == null){
            throw new IllegalArgumentException("É necessário que o livro já esteja cadastrado na base.");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }
}
