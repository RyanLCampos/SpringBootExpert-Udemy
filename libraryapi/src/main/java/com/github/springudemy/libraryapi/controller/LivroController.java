package com.github.springudemy.libraryapi.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.springudemy.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.springudemy.libraryapi.controller.dto.ErroResposta;
import com.github.springudemy.libraryapi.controller.mappers.LivroMapper;
import com.github.springudemy.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.service.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController{

    private final LivroService service; 

    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO){
        try{
            
            Livro livro = mapper.toEntity(cadastroLivroDTO);

            service.salvar(livro);

            URI location = gerarHeaderLocation(livro.getId());

            return ResponseEntity.created(location).build();
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    } 
}
