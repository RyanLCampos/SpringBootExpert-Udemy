package com.github.springudemy.libraryapi.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.springudemy.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.springudemy.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.github.springudemy.libraryapi.controller.mappers.LivroMapper;
import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.service.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService service;

    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {

        Livro livro = mapper.toEntity(cadastroLivroDTO);

        service.salvar(livro);

        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
