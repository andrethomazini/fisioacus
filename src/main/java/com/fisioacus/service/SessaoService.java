package com.fisioacus.service;

import com.fisioacus.service.dto.SessaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Sessao.
 */
public interface SessaoService {

    /**
     * Save a sessao.
     *
     * @param sessaoDTO the entity to save
     * @return the persisted entity
     */
    SessaoDTO save(SessaoDTO sessaoDTO);

    /**
     *  Get all the sessaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SessaoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" sessao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SessaoDTO findOne(Long id);

    /**
     *  Delete the "id" sessao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
