package com.fisioacus.service.mapper;

import com.fisioacus.domain.*;
import com.fisioacus.service.dto.PessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pessoa and its DTO PessoaDTO.
 */
@Mapper(componentModel = "spring", uses = {CidadeMapper.class, })
public interface PessoaMapper extends EntityMapper <PessoaDTO, Pessoa> {

    @Mapping(source = "cidade.id", target = "cidadeId")
    @Mapping(source = "cidade.descricao", target = "cidadeDescricao")
    PessoaDTO toDto(Pessoa pessoa); 

    @Mapping(source = "cidadeId", target = "cidade")
    Pessoa toEntity(PessoaDTO pessoaDTO); 
    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
