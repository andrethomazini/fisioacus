package com.fisioacus.service.mapper;

import com.fisioacus.domain.*;
import com.fisioacus.service.dto.MedicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medico and its DTO MedicoDTO.
 */
@Mapper(componentModel = "spring", uses = {EspecialidadeMapper.class, })
public interface MedicoMapper extends EntityMapper <MedicoDTO, Medico> {

    @Mapping(source = "especialidade.id", target = "especialidadeId")
    @Mapping(source = "especialidade.descricao", target = "especialidadeDescricao")
    MedicoDTO toDto(Medico medico); 

    @Mapping(source = "especialidadeId", target = "especialidade")
    Medico toEntity(MedicoDTO medicoDTO); 
    default Medico fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setId(id);
        return medico;
    }
}
