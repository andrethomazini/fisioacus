package com.fisioacus.service;

import com.fisioacus.service.dto.ProfissionalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Profissional.
 */
public interface ProfissionalService {

    /**
     * Save a profissional.
     *
     * @param profissionalDTO the entity to save
     * @return the persisted entity
     */
    ProfissionalDTO save(ProfissionalDTO profissionalDTO);

    /**
     *  Get all the profissionals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProfissionalDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" profissional.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProfissionalDTO findOne(Long id);

    /**
     *  Delete the "id" profissional.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
