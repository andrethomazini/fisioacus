package com.fisioacus.service;

import com.fisioacus.service.dto.PessoaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Pessoa.
 */
public interface PessoaService {

    /**
     * Save a pessoa.
     *
     * @param pessoaDTO the entity to save
     * @return the persisted entity
     */
    PessoaDTO save(PessoaDTO pessoaDTO);

    /**
     *  Get all the pessoas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PessoaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pessoa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PessoaDTO findOne(Long id);

    /**
     *  Delete the "id" pessoa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
