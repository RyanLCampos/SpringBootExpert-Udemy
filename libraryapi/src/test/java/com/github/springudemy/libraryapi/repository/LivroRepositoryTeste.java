package com.github.springudemy.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public void salvarAutorELivroTeste(){

        Livro livro = new Livro();

        livro.setIsbn("15783-64813");
        livro.setTitulo("repellat dolorem");
        livro.setDataPublicacao(LocalDate.of(2020, 5, 30));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(351.98));
        
        Autor autor = new Autor(); // Instanciando um objeto da classe Autor

		autor.setNome("Samir Balistreri");
		autor.setNacionalidade("Indiano");
		autor.setDataNascimento(LocalDate.of(1925, 2, 27));

        autorRepository.save(autor);

        livro.setAutor(autor);

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

    @Test
    public void atualizarAutorDoLivro(){

        UUID id = UUID.fromString("873a5ff9-2b99-4ab8-8699-829e1211a8de");

        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("565f0aa7-83ce-468c-91cb-393a713c4fb2");

        var autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    public void deletarLivro(){
        
        UUID id = UUID.fromString("330f8fa6-388e-4964-b916-13d19c907991");

        Livro livroParaDeletar = livroRepository.findById(id).orElse(null);

        livroRepository.delete(livroParaDeletar);
    }

    @Test
    public void listarLivros(){
        List<Livro> livros = livroRepository.findAll();

        livros.forEach(System.out::println);
    }

    @Test
    // @Transactional (Caso utilizar FetchType.LAZY)
    public void buscarLivroTeste(){
        UUID idLivro = UUID.fromString("2402554b-def7-4873-b7de-79459be014f3");

        Livro livro = livroRepository.findById(idLivro).orElse(null);

        System.out.println("Livro -> " + livro.getTitulo());
        System.out.println("Autor -> " + livro.getAutor().getNome());
    }

    @Test
    public void buscarLivroPorTituloTeste(){
        List<Livro> livros = livroRepository.findByTitulo("Metal Systems");

        livros.forEach(System.out::println);
    }

    @Test
    public void buscarLivroPorISBNTeste(){
        List<Livro> livros = livroRepository.findByIsbn("58657-21532");

        livros.forEach(System.out::println);
    }

    @Test
    public void buscarLivroPorTituloEPrecoTeste(){

        BigDecimal preco = BigDecimal.valueOf(648.95);

        List<Livro> livros = livroRepository.findByTituloAndPrecoOrderByTitulo("Metal Systems", preco);

        livros.forEach(System.out::println);
    }

    @Test
    public void buscarLivroPorIntervaloDePublicacaoTeste(){

        List<Livro> livros = livroRepository.findByDataPublicacaoBetween(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 2, 22));

        livros.forEach(System.out::println);
    }

    @Test
    public void listarLivrosComQueryJPQL(){
        var livros = livroRepository.listarTodosOrdenadoPorTituloAndPreco();

        livros.forEach(System.out::println);
    }

    @Test
    public void obterAutoresPorLivrosComQueryJPQL(){
        var autores = livroRepository.listarAutoresDosLivros();

        autores.forEach(System.out::println);
    }

    @Test
    public void obterTitulosNaoRepetidosDosLivros(){
        var titulos = livroRepository.listarNomesDiferentesLivros();

        titulos.forEach(System.out::println);
    }

    @Test
    public void listarGenerosDeLivrosAutoresAustralianos(){
        var generos = livroRepository.listarGenerosAutoresAustralianos();

        generos.forEach(System.out::println);
    }
}
