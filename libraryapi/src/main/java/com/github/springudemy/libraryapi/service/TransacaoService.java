package com.github.springudemy.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.repository.AutorRepository;
import com.github.springudemy.libraryapi.repository.LivroRepository;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){ // Exemplo
        // salva o livro

        // pega o id do livro = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // Atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png")
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("873a5ff9-2b99-4ab8-8699-829e1211a8de"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2021, 4, 6));

        // livroRepository.save(livro);
    }

    @Transactional
    public void executar() {

        // Salva o autor
        Autor autor = new Autor();

        autor.setNome("Teste Matheus");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1925, 2, 27));

        autorRepository.save(autor);
        // livroRepository.saveAndFlush(autor); SOMENTE QUANDO NECESSÁRIO

        // Salva o Livro
        Livro livro = new Livro();

        livro.setIsbn("15783-64813");
        livro.setTitulo("Teste Livro do Matheus");
        livro.setDataPublicacao(LocalDate.of(2020, 5, 30));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(351.98));

        livro.setAutor(autor);

        livroRepository.save(livro);
        // livroRepository.saveAndFlush(livro); SOMENTE QUANDO NECESSÁRIO

        if (autor.getNome().equals("Teste Matheus")) {
            throw new RuntimeException("Rollback!"); // Testando o Rollback.
        }
    }
}
