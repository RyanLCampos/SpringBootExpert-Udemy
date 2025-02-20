package com.github.springudemy.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.springudemy.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID>{
    
}
