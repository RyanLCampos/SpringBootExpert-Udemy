package com.github.springudemy.libraryapi.controller;

import java.net.URI;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.springudemy.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.springudemy.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.github.springudemy.libraryapi.controller.mappers.LivroMapper;
import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;
import com.github.springudemy.libraryapi.service.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
@Tag(name = "Livros")
public class LivroController implements GenericController {

    private final LivroService service;

    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo livro.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado.")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {

        Livro livro = mapper.toEntity(cadastroLivroDTO);

        service.salvar(livro);

        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do livro pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado."),
    })
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta livro existente por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<Object> deletar(@PathVariable String id) {

        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de livros por parametros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),
    })
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisar(
            @RequestParam(value = "isbn", required = false) 
            String isbn,
            @RequestParam(value = "titulo", required = false) 
            String titulo,
            @RequestParam(value = "nome-autor", required = false) 
            String nomeAutor,
            @RequestParam(value = "genero", required = false) 
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) 
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Livro> paginaResultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um livro existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado."),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado.")
    })
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto){

        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setAutor(entidadeAux.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
