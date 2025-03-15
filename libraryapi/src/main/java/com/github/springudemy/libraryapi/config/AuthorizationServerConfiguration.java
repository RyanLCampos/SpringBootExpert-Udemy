package com.github.springudemy.libraryapi.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
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

            )
            .exceptionHandling((exceptions) -> exceptions
                    .defaultAuthenticationEntryPointFor(
                            new LoginUrlAuthenticationEntryPoint("/login"),
                            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            );

        http.formLogin(configurer -> configurer.loginPage("/login"));

        http.oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()));
        
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
                .accessTokenTimeToLive(Duration.ofMinutes(60)) // access_token: token utilizado nas requisições
                .refreshTokenTimeToLive(Duration.ofMinutes(90)) // refresh_token: token para renovar o access_token
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
        KeyPair keyPair = gerarChaveRSA();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);

    }

    // Gerar par de chaves RSA
    private static KeyPair gerarChaveRSA() throws Exception {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

        return keyPair;
    }


    // Metodo que cria um decoder de JWT usando a fonte de chaves JWK
    // O JWT será validado usando o conjunto de chaves fornecido.
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource); // Cria um JwtDecoder usando a fonte de chaves JWK
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder()
                .tokenEndpoint("/oauth2/token") // Obter token
                .tokenIntrospectionEndpoint("/oauth2/introspect") // Utilizado para consultar status do token
                .tokenRevocationEndpoint("/oauth2/revoke") // revogar
                .authorizationEndpoint("/oauth2/authorize") // Authorization endpoint
                .oidcUserInfoEndpoint("/oauth2/userinfo") // Informações do usuario OPEN ID CONNECT
                .jwkSetEndpoint("/oauth2/jwks") // Obter a chave publica pra verificar a assinatura do token
                .oidcLogoutEndpoint("/oauth2/logout") //logout
                .build();
    }


}
