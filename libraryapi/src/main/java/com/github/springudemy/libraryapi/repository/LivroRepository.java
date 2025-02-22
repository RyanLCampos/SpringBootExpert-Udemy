package com.github.springudemy.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.model.Livro;
import java.math.BigDecimal;


public interface LivroRepository extends JpaRepository<Livro, UUID>{

    // Query Method
    public List<Livro> findByAutor(Autor autor); // Busca Livro por Autor

    public List<Livro> findByTitulo(String titulo); // Busca Livro por Titulo

    public List<Livro> findByIsbn(String isbn); // Busca Livro por ISBN

    public List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco); // Buscar por Titulo e Preço

}   
