package com.github.springudemy.libraryapi.controller.mappers;

import com.github.springudemy.libraryapi.controller.dto.UsuarioDTO;
import com.github.springudemy.libraryapi.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);

}
