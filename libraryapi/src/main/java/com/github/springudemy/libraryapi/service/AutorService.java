package com.github.springudemy.libraryapi.service;

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

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }
}
