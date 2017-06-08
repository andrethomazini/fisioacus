package com.fisioacus.service.impl;

import com.fisioacus.service.AtendimentoService;
import com.fisioacus.domain.Atendimento;
import com.fisioacus.repository.AtendimentoRepository;
import com.fisioacus.service.dto.AtendimentoDTO;
import com.fisioacus.service.mapper.AtendimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Atendimento.
 */
@Service
@Transactional
public class AtendimentoServiceImpl implements AtendimentoService{

    private final Logger log = LoggerFactory.getLogger(AtendimentoServiceImpl.class);

    private final AtendimentoRepository atendimentoRepository;

    private final AtendimentoMapper atendimentoMapper;

    public AtendimentoServiceImpl(AtendimentoRepository atendimentoRepository, AtendimentoMapper atendimentoMapper) {
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoMapper = atendimentoMapper;
    }

    /**
     * Save a atendimento.
     *
     * @param atendimentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AtendimentoDTO save(AtendimentoDTO atendimentoDTO) {
        log.debug("Request to save Atendimento : {}", atendimentoDTO);
        Atendimento atendimento = atendimentoMapper.toEntity(atendimentoDTO);
        atendimento = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toDto(atendimento);
    }

    /**
     *  Get all the atendimentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Atendimentos");
        return atendimentoRepository.findAll(pageable)
            .map(atendimentoMapper::toDto);
    }

    /**
     *  Get one atendimento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AtendimentoDTO findOne(Long id) {
        log.debug("Request to get Atendimento : {}", id);
        Atendimento atendimento = atendimentoRepository.findOne(id);
        return atendimentoMapper.toDto(atendimento);
    }

    /**
     *  Delete the  atendimento by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Atendimento : {}", id);
        atendimentoRepository.delete(id);
    }
}
