package com.github.springudemy.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import com.github.springudemy.libraryapi.controller.dto.AutorDTO;
import com.github.springudemy.libraryapi.model.Autor;

@Mapper(componentModel = "spring") // componentModel = "spring" -> Gera um componente Spring durante a compilação
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);

}
