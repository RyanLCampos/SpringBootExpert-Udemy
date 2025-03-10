package com.github.springudemy.libraryapi.controller;

import com.github.springudemy.libraryapi.controller.dto.ClientDTO;
import com.github.springudemy.libraryapi.controller.mappers.ClientMapper;
import com.github.springudemy.libraryapi.model.Client;
import com.github.springudemy.libraryapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody @Valid ClientDTO dto){
        Client client = mapper.toEntity(dto);

        service.salvar(client);
    }

}
