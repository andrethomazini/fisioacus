package com.fisioacus.service.impl;

import com.fisioacus.service.SessaoService;
import com.fisioacus.domain.Sessao;
import com.fisioacus.repository.SessaoRepository;
import com.fisioacus.service.dto.SessaoDTO;
import com.fisioacus.service.mapper.SessaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Sessao.
 */
@Service
@Transactional
public class SessaoServiceImpl implements SessaoService{

    private final Logger log = LoggerFactory.getLogger(SessaoServiceImpl.class);

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;

    public SessaoServiceImpl(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
    }

    /**
     * Save a sessao.
     *
     * @param sessaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SessaoDTO save(SessaoDTO sessaoDTO) {
        log.debug("Request to save Sessao : {}", sessaoDTO);
        Sessao sessao = sessaoMapper.toEntity(sessaoDTO);
        sessao = sessaoRepository.save(sessao);
        return sessaoMapper.toDto(sessao);
    }

    /**
     *  Get all the sessaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SessaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sessaos");
        return sessaoRepository.findAll(pageable)
            .map(sessaoMapper::toDto);
    }

    /**
     *  Get one sessao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SessaoDTO findOne(Long id) {
        log.debug("Request to get Sessao : {}", id);
        Sessao sessao = sessaoRepository.findOne(id);
        return sessaoMapper.toDto(sessao);
    }

    /**
     *  Delete the  sessao by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sessao : {}", id);
        sessaoRepository.delete(id);
    }
}
