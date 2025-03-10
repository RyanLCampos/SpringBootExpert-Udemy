package com.github.springudemy.libraryapi.validator;

import com.github.springudemy.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.springudemy.libraryapi.model.Client;
import com.github.springudemy.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository repository;

    public void validar(Client client){
        if(existeClientId(client)){
            throw new RegistroDuplicadoException("Client já cadastrado!");
        }
    }

    private boolean existeClientId(Client client) {
        Optional<Client> optionalClient = repository.findByClientId(client.getClientId());

        // Quando estiver cadastrando
        if(client.getId() == null){
            return optionalClient.isPresent();
        }

        // Quando o client está sendo atualizado
        return optionalClient
                .map(Client::getId) // Obtem o ID do client encontrado
                .filter(id -> !id.equals(client.getId())) // Filtra se o ID encontrado é diferente do atual
                .isPresent(); // Se present, significa que há outro client com o mesmo clientId e ID diferente
    }

}
