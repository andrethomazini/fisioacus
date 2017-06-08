package com.fisioacus.service;

import com.fisioacus.service.dto.ConvenioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Convenio.
 */
public interface ConvenioService {

    /**
     * Save a convenio.
     *
     * @param convenioDTO the entity to save
     * @return the persisted entity
     */
    ConvenioDTO save(ConvenioDTO convenioDTO);

    /**
     *  Get all the convenios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConvenioDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" convenio.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConvenioDTO findOne(Long id);

    /**
     *  Delete the "id" convenio.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
