package com.github.springudemy.libraryapi.service;

import org.springframework.stereotype.Service;

import com.github.springudemy.libraryapi.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    @SuppressWarnings("unused")
    private final LivroRepository livroRepository;
}
