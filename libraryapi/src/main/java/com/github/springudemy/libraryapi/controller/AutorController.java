package com.github.springudemy.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.springudemy.libraryapi.controller.dto.AutorDTO;
import com.github.springudemy.libraryapi.controller.dto.ErroResposta;
import com.github.springudemy.libraryapi.exceptions.AutorComObraAssociadaException;
import com.github.springudemy.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.service.AutorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autores")
// http://localhost:8080/autores
public class AutorController {

    private final AutorService autorService;

    // @RequestMapping(method = RequestMethod.POST) - OUTRA FORMA
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor) {
        try {

            Autor autorEntidade = autor.mapearParaAutor();

            autorService.salvar(autorEntidade);

            // http://localhost:8080/autores/873a5ff9-2b99-4ab8-8699-829e1211a8de
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {

        Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();

            AutorDTO autorDTO = new AutorDTO(autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());

            return ResponseEntity.ok(autorDTO); // Encontrado -> Código: 200
        }

        return ResponseEntity.notFound().build(); // Não encontrado -> Código: 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirAutor(@PathVariable String id) {
        try {
            Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));
            
            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Autor autor = autorOptional.get();
            
            autorService.deletar(autor);
            
            return ResponseEntity.noContent().build();

        }catch(AutorComObraAssociadaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);

        List<AutorDTO> lista = resultado
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        try {

            Optional<Autor> autorOptional = autorService.obterPorId(UUID.fromString(id));

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();

            autor.setNome(dto.nome());
            autor.setDataNascimento(dto.dataNascimento());
            autor.setNacionalidade(dto.nacionalidade());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}
