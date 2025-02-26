package com.github.springudemy.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.springudemy.libraryapi.exceptions.AutorComObraAssociadaException;
import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.repository.AutorRepository;
import com.github.springudemy.libraryapi.repository.LivroRepository;
import com.github.springudemy.libraryapi.validator.AutorValidator;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator validator;

    public AutorService(AutorRepository autorRepository, AutorValidator validator, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }

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
            throw new AutorComObraAssociadaException("Autor possui livro associada!");
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

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
