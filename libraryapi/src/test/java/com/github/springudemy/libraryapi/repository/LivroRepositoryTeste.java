package com.github.springudemy.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;

@SpringBootTest
public class LivroRepositoryTeste {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void salvarTeste(){

        Livro livro = new Livro();

        livro.setIsbn("51355-12458");
        livro.setTitulo("doloribus dicta");
        livro.setDataPublicacao(LocalDate.of(2019, 1, 9));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(255.98));
        
        var id = UUID.fromString("5cbdd2db-3e56-4e46-b61d-e01c28582334");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autor = possivelAutor.get();

            livro.setAutor(autor);
        }else{
            System.out.println("Autor nao encontrado!");
        }

        livroRepository.save(livro);

    }


    @Test
    public void salvarCascadeTeste(){

        Livro livro = new Livro();

        livro.setIsbn("51355-12458");
        livro.setTitulo("doloribus dicta");
        livro.setDataPublicacao(LocalDate.of(2019, 1, 9));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(255.98));
        
        Autor autor = new Autor(); // Instanciando um objeto da classe Autor

		autor.setNome("Alexandre Heathcote");
		autor.setNacionalidade("Australiano");
		autor.setDataNascimento(LocalDate.of(1975, 6, 10));

        livro.setAutor(autor);

        livroRepository.save(livro);

    }
}
