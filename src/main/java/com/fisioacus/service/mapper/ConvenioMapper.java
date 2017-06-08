package com.fisioacus.service.mapper;

import com.fisioacus.domain.*;
import com.fisioacus.service.dto.ConvenioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Convenio and its DTO ConvenioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConvenioMapper extends EntityMapper <ConvenioDTO, Convenio> {
    
    
    default Convenio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Convenio convenio = new Convenio();
        convenio.setId(id);
        return convenio;
    }
}
