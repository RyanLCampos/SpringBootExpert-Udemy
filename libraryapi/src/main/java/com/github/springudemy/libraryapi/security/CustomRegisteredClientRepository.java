package com.github.springudemy.libraryapi.security;

import com.github.springudemy.libraryapi.model.Client;
import com.github.springudemy.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementação personalizada do repositório RegisteredClientRepository.
 * Utiliza o ClientService para buscar dados do cliente por clientId e converte em um objeto RegisteredClient.
 * <p>
 * Métodos:
 * - `save`: Não implementado (salvar um RegisteredClient).
 * - `findById`: Não implementado (retorna null).
 * - `findByClientId`: Busca um cliente e retorna um RegisteredClient mapeado.
 * <p>
 * Em resumo, a classe é responsável por transformar as informações de um cliente em um formato compatível com a autenticação OAuth 2.0,
 * permitindo que os dados do cliente sejam utilizados em fluxos de autorização.
 */


@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService service;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {

        Optional<Client> optionalClient = service.obterPorClienteID(clientId);

        if(optionalClient.isEmpty()){
            return null;
        }

        Client client = optionalClient.get();

        return RegisteredClient
                .withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .redirectUri(client.getRedirectURI())
                .scope(client.getScope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // Define o metodo de autenticação do cliente
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Define que o cliente usará o fluxo de "Código de Autorização" para obter o token de acesso
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .build();
    }
}
