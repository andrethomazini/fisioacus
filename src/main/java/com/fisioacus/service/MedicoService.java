package com.fisioacus.service;

import com.fisioacus.service.dto.MedicoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Medico.
 */
public interface MedicoService {

    /**
     * Save a medico.
     *
     * @param medicoDTO the entity to save
     * @return the persisted entity
     */
    MedicoDTO save(MedicoDTO medicoDTO);

    /**
     *  Get all the medicos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MedicoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" medico.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MedicoDTO findOne(Long id);

    /**
     *  Delete the "id" medico.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
