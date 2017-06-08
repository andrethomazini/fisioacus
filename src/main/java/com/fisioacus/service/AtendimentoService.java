package com.fisioacus.service;

import com.fisioacus.service.dto.AtendimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Atendimento.
 */
public interface AtendimentoService {

    /**
     * Save a atendimento.
     *
     * @param atendimentoDTO the entity to save
     * @return the persisted entity
     */
    AtendimentoDTO save(AtendimentoDTO atendimentoDTO);

    /**
     *  Get all the atendimentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AtendimentoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" atendimento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AtendimentoDTO findOne(Long id);

    /**
     *  Delete the "id" atendimento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
