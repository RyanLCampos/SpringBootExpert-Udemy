package com.github.springudemy.libraryapi.config;

import com.github.springudemy.libraryapi.security.CustomRegisteredClientRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

/**
 * Configuração do servidor de autorização.
 * Define os beans para configurar as definições de token e cliente no servidor de autorização OAuth2.
 * <p>
 * Beans:
 * - `tokenSettings`: configura o formato do token e o tempo de expiração do token de acesso.
 * - `clientSettings`: configura as opções relacionadas ao cliente, como a necessidade de consentimento de autorização.
 */


@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception{

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
        http
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, (authorizationServer) ->
                    authorizationServer
                            .oidc(Customizer.withDefaults())
            );

        http.oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()));

        http.formLogin(configurer -> configurer.loginPage("/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public TokenSettings tokenSettings(){
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .build();
    }

    @Bean
    public ClientSettings clientSettings(){
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }


    // JWK -> JSON Web Key
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = gerarChaveRSA(); // Gera o par de chaves RSA
        JWKSet jwkSet = new JWKSet(rsaKey); // Cria um conjunto de chaves JWK
        return new ImmutableJWKSet<>(jwkSet); // Retorna o conjunto de chaves JWK (Imutável)
    }

    // Gerar par de chaves RSA
    private RSAKey gerarChaveRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // Inicializa o gerador de chaves RSA
        keyPairGenerator.initialize(2048); // Define o tamanho da chave como 2048 bits
        KeyPair keyPair = keyPairGenerator.generateKeyPair(); // Gera o par de chaves (Publica e Privada)

        RSAPublicKey chavePublica = (RSAPublicKey) keyPair.getPublic(); // Obtém chave publica

        RSAPrivateKey chavePrivada = (RSAPrivateKey) keyPair.getPrivate(); // Obtém chave privada

        // Cria e retorna uma chave RSA com chave publica, chave privada e um identificador único.
        return new RSAKey
                .Builder(chavePublica)
                .privateKey(chavePrivada)
                .keyID(UUID.randomUUID().toString()) // Gera um UUID para o Key ID
                .build();
    }


    // Metodo que cria um decoder de JWT usando a fonte de chaves JWK
    // O JWT será validado usando o conjunto de chaves fornecido.
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource); // Cria um JwtDecoder usando a fonte de chaves JWK
    }

}
