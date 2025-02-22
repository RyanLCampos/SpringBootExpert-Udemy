package com.github.springudemy.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;

@SpringBootTest
public class AutorRepositoryTeste{

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTeste(){
        Autor autor = new Autor(); // Instanciando um objeto da classe Autor

		autor.setNome("Anika Klein");
		autor.setNacionalidade("Americano");
		autor.setDataNascimento(LocalDate.of(1998, 2, 13));

		Autor autorSalvo = autorRepository.save(autor);

		System.out.println("Autor Salvo -> " + autorSalvo);
    }

    @Test
    public void atualizarTeste(){
        var id = UUID.fromString("d5645f41-2398-4eda-97b9-8ed11b089395");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("===== Dados do Autor =====");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(2003, 06, 01));


            autorRepository.save(autorEncontrado);
        }else{
            System.out.println("Autor nao encontrado!");
        }

    }

    @Test
    public void listarTodos(){
        
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de Autores -> " + autorRepository.count());
    }

    @Test
    public void deletarPorIdTeste(){
        var id = UUID.fromString("7fad2460-cd0a-46ce-9ba2-9d2ab39c8fce");

        autorRepository.deleteById(id);
    }

    @Test
    public void deleteTeste(){
        var id = UUID.fromString("d5645f41-2398-4eda-97b9-8ed11b089395");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEcontrado = possivelAutor.get();
            
            autorRepository.delete(autorEcontrado);
        }
    }

    @Test
    public void salvarAutorComLivrosTeste(){
        Autor autor = new Autor();

        autor.setNome("Jerald Schamberger");
        autor.setNacionalidade("Argentino");
        autor.setDataNascimento(LocalDate.of(2003, 3, 3));
        
        autorRepository.save(autor);

        Livro livro = new Livro();

        livro.setIsbn("13476-34863");
        livro.setTitulo("Metal Systems");
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(648.95));
        livro.setDataPublicacao(LocalDate.of(2025, 1, 25));
        livro.setAutor(autor);

        Livro livro2 = new Livro();

        livro2.setIsbn("58657-21532");
        livro2.setTitulo("Teal Cambridgeshire");
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setPreco(BigDecimal.valueOf(150.45));
        livro2.setDataPublicacao(LocalDate.of(2025, 2, 15));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);

        livroRepository.saveAll(autor.getLivros()); // Remover caso utilizar cascade
    }

    @Test
    // @Transactional // (Caso o FetchType.EAGER)
    public void listarLivrosAutorTeste(){
        
        UUID idAutor = UUID.fromString("5cbdd2db-3e56-4e46-b61d-e01c28582334");
        
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        // Utilizar Query Method no lugar de FetchType.EAGER (NESSE CASO)
        // Irá puxar os livros somente quando requisitado.
        List<Livro> livros = livroRepository.findByAutor(autor); // Query Method
        
        autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);
    }
}
