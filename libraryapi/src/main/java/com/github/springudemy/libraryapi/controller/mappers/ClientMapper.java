package com.github.springudemy.libraryapi.controller.mappers;

import com.github.springudemy.libraryapi.controller.dto.ClientDTO;
import com.github.springudemy.libraryapi.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client toEntity(ClientDTO dto);

}
