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
import com.github.springudemy.libraryapi.exceptions.AutorComObraAssociadaException;
import com.github.springudemy.libraryapi.exceptions.CampoInvalidoException;
import com.github.springudemy.libraryapi.exceptions.RegistroDuplicadoException;

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

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e) {
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AutorComObraAssociadaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleAutorComObraAssociadaException(AutorComObraAssociadaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado, entre em contato com a administração.",
                List.of());
    }

}
