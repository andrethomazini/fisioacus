package com.fisioacus.service.impl;

import com.fisioacus.service.ConvenioService;
import com.fisioacus.domain.Convenio;
import com.fisioacus.repository.ConvenioRepository;
import com.fisioacus.service.dto.ConvenioDTO;
import com.fisioacus.service.mapper.ConvenioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Convenio.
 */
@Service
@Transactional
public class ConvenioServiceImpl implements ConvenioService{

    private final Logger log = LoggerFactory.getLogger(ConvenioServiceImpl.class);

    private final ConvenioRepository convenioRepository;

    private final ConvenioMapper convenioMapper;

    public ConvenioServiceImpl(ConvenioRepository convenioRepository, ConvenioMapper convenioMapper) {
        this.convenioRepository = convenioRepository;
        this.convenioMapper = convenioMapper;
    }

    /**
     * Save a convenio.
     *
     * @param convenioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConvenioDTO save(ConvenioDTO convenioDTO) {
        log.debug("Request to save Convenio : {}", convenioDTO);
        Convenio convenio = convenioMapper.toEntity(convenioDTO);
        convenio = convenioRepository.save(convenio);
        return convenioMapper.toDto(convenio);
    }

    /**
     *  Get all the convenios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConvenioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Convenios");
        return convenioRepository.findAll(pageable)
            .map(convenioMapper::toDto);
    }

    /**
     *  Get one convenio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConvenioDTO findOne(Long id) {
        log.debug("Request to get Convenio : {}", id);
        Convenio convenio = convenioRepository.findOne(id);
        return convenioMapper.toDto(convenio);
    }

    /**
     *  Delete the  convenio by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Convenio : {}", id);
        convenioRepository.delete(id);
    }
}
