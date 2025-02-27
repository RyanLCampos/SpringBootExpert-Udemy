package com.github.springudemy.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.github.springudemy.libraryapi.controller.dto.AutorDTO;
import com.github.springudemy.libraryapi.model.Autor;

//componentModel = "spring" -> Gera um componente Spring durante a compilação
// unmappedTargetPolicy = ReportingPolicy.IGNORE -> Irá ignorar os targets que não foram mapeados.
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
