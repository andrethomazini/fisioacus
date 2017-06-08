package com.fisioacus.service.impl;

import com.fisioacus.service.ProfissionalService;
import com.fisioacus.domain.Profissional;
import com.fisioacus.repository.ProfissionalRepository;
import com.fisioacus.service.dto.ProfissionalDTO;
import com.fisioacus.service.mapper.ProfissionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Profissional.
 */
@Service
@Transactional
public class ProfissionalServiceImpl implements ProfissionalService{

    private final Logger log = LoggerFactory.getLogger(ProfissionalServiceImpl.class);

    private final ProfissionalRepository profissionalRepository;

    private final ProfissionalMapper profissionalMapper;

    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository, ProfissionalMapper profissionalMapper) {
        this.profissionalRepository = profissionalRepository;
        this.profissionalMapper = profissionalMapper;
    }

    /**
     * Save a profissional.
     *
     * @param profissionalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfissionalDTO save(ProfissionalDTO profissionalDTO) {
        log.debug("Request to save Profissional : {}", profissionalDTO);
        Profissional profissional = profissionalMapper.toEntity(profissionalDTO);
        profissional = profissionalRepository.save(profissional);
        return profissionalMapper.toDto(profissional);
    }

    /**
     *  Get all the profissionals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfissionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profissionals");
        return profissionalRepository.findAll(pageable)
            .map(profissionalMapper::toDto);
    }

    /**
     *  Get one profissional by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProfissionalDTO findOne(Long id) {
        log.debug("Request to get Profissional : {}", id);
        Profissional profissional = profissionalRepository.findOne(id);
        return profissionalMapper.toDto(profissional);
    }

    /**
     *  Delete the  profissional by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profissional : {}", id);
        profissionalRepository.delete(id);
    }
}
