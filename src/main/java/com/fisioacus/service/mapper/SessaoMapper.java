package com.fisioacus.service.mapper;

import com.fisioacus.domain.*;
import com.fisioacus.service.dto.SessaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sessao and its DTO SessaoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfissionalMapper.class, AtendimentoMapper.class, })
public interface SessaoMapper extends EntityMapper <SessaoDTO, Sessao> {

    @Mapping(source = "profissional.id", target = "profissionalId")

    @Mapping(source = "atendimento.id", target = "atendimentoId")
    SessaoDTO toDto(Sessao sessao); 

    @Mapping(source = "profissionalId", target = "profissional")

    @Mapping(source = "atendimentoId", target = "atendimento")
    Sessao toEntity(SessaoDTO sessaoDTO); 
    default Sessao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sessao sessao = new Sessao();
        sessao.setId(id);
        return sessao;
    }
}
