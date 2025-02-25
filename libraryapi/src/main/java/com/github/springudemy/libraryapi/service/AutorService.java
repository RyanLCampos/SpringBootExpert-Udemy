package com.github.springudemy.libraryapi.service;

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
}
