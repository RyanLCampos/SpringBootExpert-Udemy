package com.github.springudemy.libraryapi.controller;

import com.github.springudemy.libraryapi.controller.dto.ClientDTO;
import com.github.springudemy.libraryapi.controller.mappers.ClientMapper;
import com.github.springudemy.libraryapi.model.Client;
import com.github.springudemy.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo client.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Client já cadastrado.")
    })
    public void salvar(@RequestBody @Valid ClientDTO dto){
        Client client = mapper.toEntity(dto);

        log.info("Registrando novo Client: {} com scope: {}", client.getClientId(), client.getScope());

        service.salvar(client);
    }

}
