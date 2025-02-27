package com.github.springudemy.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.github.springudemy.libraryapi.exceptions.AutorComObraAssociadaException;
import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.repository.AutorRepository;
import com.github.springudemy.libraryapi.repository.LivroRepository;
import com.github.springudemy.libraryapi.validator.AutorValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Somente campos com final, não precisa de construtor.
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator validator;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("É necessário que o autor já esteja cadastrado na base.");
        }
        
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new AutorComObraAssociadaException("Não foi possível excluir o autor. Existem livros associados a este autor que impedem a exclusão.");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null || nacionalidade != null){
            return autorRepository.findByNomeOrNacionalidade(nome, nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        
        var autor = new Autor();
        
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues() // Ignora valores null
                .withIgnoreCase() // Ignora se é maiusculo ou minusculo 
                .withStringMatcher(StringMatcher.CONTAINING); // Verifica se o nome do autor (Banco) contém o valor especificado

        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
