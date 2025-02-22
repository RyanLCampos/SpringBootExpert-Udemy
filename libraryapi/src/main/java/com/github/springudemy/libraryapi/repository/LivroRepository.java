package com.github.springudemy.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID>{

    // Query Method
    public List<Livro> findByAutor(Autor autor);

}   
