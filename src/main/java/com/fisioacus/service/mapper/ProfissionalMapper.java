package com.fisioacus.service.mapper;

import com.fisioacus.domain.*;
import com.fisioacus.service.dto.ProfissionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profissional and its DTO ProfissionalDTO.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, })
public interface ProfissionalMapper extends EntityMapper <ProfissionalDTO, Profissional> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    ProfissionalDTO toDto(Profissional profissional); 

    @Mapping(source = "pessoaId", target = "pessoa")
    Profissional toEntity(ProfissionalDTO profissionalDTO); 
    default Profissional fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profissional profissional = new Profissional();
        profissional.setId(id);
        return profissional;
    }
}
