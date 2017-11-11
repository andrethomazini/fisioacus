package com.fisioacus.service;

import com.fisioacus.service.dto.EspecialidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Especialidade.
 */
public interface EspecialidadeService {

    /**
     * Save a especialidade.
     *
     * @param especialidadeDTO the entity to save
     * @return the persisted entity
     */
    EspecialidadeDTO save(EspecialidadeDTO especialidadeDTO);

    /**
     *  Get all the especialidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EspecialidadeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" especialidade.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EspecialidadeDTO findOne(Long id);

    /**
     *  Delete the "id" especialidade.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
