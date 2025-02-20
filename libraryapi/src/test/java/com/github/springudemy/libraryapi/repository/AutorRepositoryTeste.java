package com.github.springudemy.libraryapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.springudemy.libraryapi.model.Autor;

@SpringBootTest
public class AutorRepositoryTeste{

    @Autowired
    private AutorRepository autorRepository;

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

}
