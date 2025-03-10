package com.github.springudemy.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

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
