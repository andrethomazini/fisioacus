package com.fisioacus.service.impl;

import com.fisioacus.service.ProcedimentoService;
import com.fisioacus.domain.Procedimento;
import com.fisioacus.repository.ProcedimentoRepository;
import com.fisioacus.service.dto.ProcedimentoDTO;
import com.fisioacus.service.mapper.ProcedimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Procedimento.
 */
@Service
@Transactional
public class ProcedimentoServiceImpl implements ProcedimentoService{

    private final Logger log = LoggerFactory.getLogger(ProcedimentoServiceImpl.class);

    private final ProcedimentoRepository procedimentoRepository;

    private final ProcedimentoMapper procedimentoMapper;

    public ProcedimentoServiceImpl(ProcedimentoRepository procedimentoRepository, ProcedimentoMapper procedimentoMapper) {
        this.procedimentoRepository = procedimentoRepository;
        this.procedimentoMapper = procedimentoMapper;
    }

    /**
     * Save a procedimento.
     *
     * @param procedimentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProcedimentoDTO save(ProcedimentoDTO procedimentoDTO) {
        log.debug("Request to save Procedimento : {}", procedimentoDTO);
        Procedimento procedimento = procedimentoMapper.toEntity(procedimentoDTO);
        procedimento = procedimentoRepository.save(procedimento);
        return procedimentoMapper.toDto(procedimento);
    }

    /**
     *  Get all the procedimentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProcedimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procedimentos");
        return procedimentoRepository.findAll(pageable)
            .map(procedimentoMapper::toDto);
    }

    /**
     *  Get one procedimento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProcedimentoDTO findOne(Long id) {
        log.debug("Request to get Procedimento : {}", id);
        Procedimento procedimento = procedimentoRepository.findOne(id);
        return procedimentoMapper.toDto(procedimento);
    }

    /**
     *  Delete the  procedimento by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Procedimento : {}", id);
        procedimentoRepository.delete(id);
    }
}
