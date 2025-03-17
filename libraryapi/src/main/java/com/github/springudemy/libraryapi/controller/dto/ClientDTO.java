package com.github.springudemy.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Schema(name = "Client")
public record ClientDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        String clientId,
        @NotBlank(message = "campo obrigatório")
        String clientSecret,
        @NotBlank(message = "campo obrigatório")
        String redirectURI,
        @NotBlank(message = "campo obrigatório")
        String scope

) {
}
