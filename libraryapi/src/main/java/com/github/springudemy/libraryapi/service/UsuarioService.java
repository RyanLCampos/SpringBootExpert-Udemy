package com.github.springudemy.libraryapi.service;

import com.github.springudemy.libraryapi.model.Usuario;
import com.github.springudemy.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder; // Para criptografar senha do usuario

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha(); // Recebe a senha

        usuario.setSenha(encoder.encode(senha)); // Criptografa a senha

        repository.save(usuario); // Salvar o usuario
    }

    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }

}
