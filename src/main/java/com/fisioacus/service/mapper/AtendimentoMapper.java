package com.fisioacus.service.mapper;

import com.fisioacus.domain.*;
import com.fisioacus.service.dto.AtendimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Atendimento and its DTO AtendimentoDTO.
 */
@Mapper(componentModel = "spring", uses = {ConvenioMapper.class, PessoaMapper.class, ProcedimentoMapper.class, MedicoMapper.class, })
public interface AtendimentoMapper extends EntityMapper <AtendimentoDTO, Atendimento> {

    @Mapping(source = "convenio.id", target = "convenioId")
    @Mapping(source = "convenio.descricao", target = "convenioDescricao")

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")

    @Mapping(source = "procedimento.id", target = "procedimentoId")
    @Mapping(source = "procedimento.descricao", target = "procedimentoDescricao")

    @Mapping(source = "medico.id", target = "medicoId")
    @Mapping(source = "medico.nome", target = "medicoNome")
    AtendimentoDTO toDto(Atendimento atendimento); 

    @Mapping(source = "convenioId", target = "convenio")

    @Mapping(source = "pessoaId", target = "pessoa")

    @Mapping(source = "procedimentoId", target = "procedimento")

    @Mapping(source = "medicoId", target = "medico")
    @Mapping(target = "sessaos", ignore = true)
    Atendimento toEntity(AtendimentoDTO atendimentoDTO); 
    default Atendimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Atendimento atendimento = new Atendimento();
        atendimento.setId(id);
        return atendimento;
    }
}
