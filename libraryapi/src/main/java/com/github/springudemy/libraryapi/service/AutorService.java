package com.github.springudemy.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;

    }

    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("É necessário que o autor já esteja cadastrado na base.");
        }
        
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
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
}
