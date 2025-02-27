package com.github.springudemy.libraryapi.service;

import org.springframework.stereotype.Service;

import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro){

        return livroRepository.save(livro);
    }
}
