package com.fisioacus.service;

import com.fisioacus.service.dto.ProcedimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Procedimento.
 */
public interface ProcedimentoService {

    /**
     * Save a procedimento.
     *
     * @param procedimentoDTO the entity to save
     * @return the persisted entity
     */
    ProcedimentoDTO save(ProcedimentoDTO procedimentoDTO);

    /**
     *  Get all the procedimentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProcedimentoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" procedimento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProcedimentoDTO findOne(Long id);

    /**
     *  Delete the "id" procedimento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
