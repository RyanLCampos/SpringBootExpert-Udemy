package com.github.springudemy.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;
import java.math.BigDecimal;
import java.time.LocalDate;



/**
 * @see LivroRepositoryTeste
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>{

    // Query Method
    public List<Livro> findByAutor(Autor autor); // Busca Livro por Autor

    public List<Livro> findByTitulo(String titulo); // Busca Livro por Titulo

    public List<Livro> findByIsbn(String isbn); // Busca Livro por ISBN

    public List<Livro> findByTituloAndPrecoOrderByTitulo(String titulo, BigDecimal preco); // Buscar por Titulo e Preço (Ordenado pelo Titulo)

    public List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim); // Buscar livros publicados entre as datas fornecidas

    // JPQL -> referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    public List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query(" select a from Livro as l join l.autor a ")
    public List<Autor> listarAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro as l ")
    public List<String> listarNomesDiferentesLivros();

    @Query("""
        select distinct l.genero
        from Livro as l
        join l.autor a
        where a.nacionalidade = 'Australiano'
        order by l.genero   
    """)
    public List<String> listarGenerosAutoresAustralianos();

    @Query(" select l from Livro as l where l.genero = :genero order by :paramOrdenacao ") // Passando parametro no @Query
    public List<Livro> findByGenero(
                    @Param("genero") GeneroLivro generoLivro, 
                    @Param("paramOrdenacao") String nomeDaPropriedade
    );
    
    // Positional Parameters
    @Query(" select l from Livro as l where l.genero = ?1 order by ?2 ") // Passando parametro no @Query
    public List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomeDaPropriedade);

    @Modifying
    @Transactional // Utilizar quando modificar dado 
    @Query(" delete from Livro where genero = ?1")
    public void deleteByGenero(GeneroLivro genero);
}   
