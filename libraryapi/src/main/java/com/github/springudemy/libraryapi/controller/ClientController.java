package com.github.springudemy.libraryapi.controller;

import com.github.springudemy.libraryapi.controller.dto.ClientDTO;
import com.github.springudemy.libraryapi.controller.mappers.ClientMapper;
import com.github.springudemy.libraryapi.model.Client;
import com.github.springudemy.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody ClientDTO dto){
        Client client = mapper.toEntity(dto);

        service.salvar(client);
    }

}
