package com.fisioacus.service.impl;

import com.fisioacus.service.EspecialidadeService;
import com.fisioacus.domain.Especialidade;
import com.fisioacus.repository.EspecialidadeRepository;
import com.fisioacus.service.dto.EspecialidadeDTO;
import com.fisioacus.service.mapper.EspecialidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Especialidade.
 */
@Service
@Transactional
public class EspecialidadeServiceImpl implements EspecialidadeService{

    private final Logger log = LoggerFactory.getLogger(EspecialidadeServiceImpl.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeMapper especialidadeMapper;

    public EspecialidadeServiceImpl(EspecialidadeRepository especialidadeRepository, EspecialidadeMapper especialidadeMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeMapper = especialidadeMapper;
    }

    /**
     * Save a especialidade.
     *
     * @param especialidadeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EspecialidadeDTO save(EspecialidadeDTO especialidadeDTO) {
        log.debug("Request to save Especialidade : {}", especialidadeDTO);
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDTO);
        especialidade = especialidadeRepository.save(especialidade);
        return especialidadeMapper.toDto(especialidade);
    }

    /**
     *  Get all the especialidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EspecialidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Especialidades");
        return especialidadeRepository.findAll(pageable)
            .map(especialidadeMapper::toDto);
    }

    /**
     *  Get one especialidade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EspecialidadeDTO findOne(Long id) {
        log.debug("Request to get Especialidade : {}", id);
        Especialidade especialidade = especialidadeRepository.findOne(id);
        return especialidadeMapper.toDto(especialidade);
    }

    /**
     *  Delete the  especialidade by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.delete(id);
    }
}
