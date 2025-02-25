package com.github.springudemy.libraryapi.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.springudemy.libraryapi.controller.dto.AutorDTO;
import com.github.springudemy.libraryapi.model.Autor;
import com.github.springudemy.libraryapi.service.AutorService;

@RestController
@RequestMapping("/autores")
// http://localhost:8080/autores
public class AutorController {

    private AutorService autorService;

    // Injeção de Dependencia
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // @RequestMapping(method = RequestMethod.POST) - OUTRA FORMA
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        Autor autorEntidade = autor.mapearParaAutor();

        autorService.salvar(autorEntidade);

        // http://localhost:8080/autores/873a5ff9-2b99-4ab8-8699-829e1211a8de
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
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

}
