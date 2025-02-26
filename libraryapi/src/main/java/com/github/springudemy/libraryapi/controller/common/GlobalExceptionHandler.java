package com.github.springudemy.libraryapi.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.springudemy.libraryapi.controller.dto.ErroCampo;
import com.github.springudemy.libraryapi.controller.dto.ErroResposta;

@RestControllerAdvice // Captura exceptions e retorna uma resposta REST
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // Captura o erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Código fixo
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ErroCampo> listaDeErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.", listaDeErros);
    }

}
