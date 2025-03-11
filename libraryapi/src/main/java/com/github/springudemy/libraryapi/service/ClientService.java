package com.github.springudemy.libraryapi.service;

import com.github.springudemy.libraryapi.model.Client;
import com.github.springudemy.libraryapi.repository.ClientRepository;
import com.github.springudemy.libraryapi.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientValidator validator;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        validator.validar(client);
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Optional<Client> obterPorClienteID(String clientId){
        return repository.findByClientId(clientId);
    }
}
