package com.github.springudemy.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.springudemy.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID>{
    
    List<Autor> findByNomeOrNacionalidade(String nome, String nacionalidade); // Busca por nome ou nacionalidade
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade); // Busca por nome e nacionalidade
}
