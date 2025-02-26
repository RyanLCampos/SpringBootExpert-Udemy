package com.github.springudemy.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.springudemy.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository repository;

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        // Verifica se o autor (Parametro) não possui id (Criação)
        if(autor.getId() == null){ 
            return autorEncontrado.isPresent(); // Se for encontrado, já está cadastrado.
        }

        // Verifica se o autor está atualizando e se o id não coincide
        return autorEncontrado.isPresent() && !autor.getId().equals(autorEncontrado.get().getId());
    }

}
