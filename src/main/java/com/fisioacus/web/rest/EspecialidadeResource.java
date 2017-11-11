package com.fisioacus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fisioacus.service.EspecialidadeService;
import com.fisioacus.web.rest.util.HeaderUtil;
import com.fisioacus.web.rest.util.PaginationUtil;
import com.fisioacus.service.dto.EspecialidadeDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Especialidade.
 */
@RestController
@RequestMapping("/api")
public class EspecialidadeResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeResource.class);

    private static final String ENTITY_NAME = "especialidade";

    private final EspecialidadeService especialidadeService;

    public EspecialidadeResource(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    /**
     * POST  /especialidades : Create a new especialidade.
     *
     * @param especialidadeDTO the especialidadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especialidadeDTO, or with status 400 (Bad Request) if the especialidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especialidades")
    @Timed
    public ResponseEntity<EspecialidadeDTO> createEspecialidade(@Valid @RequestBody EspecialidadeDTO especialidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Especialidade : {}", especialidadeDTO);
        if (especialidadeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new especialidade cannot already have an ID")).body(null);
        }
        EspecialidadeDTO result = especialidadeService.save(especialidadeDTO);
        return ResponseEntity.created(new URI("/api/especialidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especialidades : Updates an existing especialidade.
     *
     * @param especialidadeDTO the especialidadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especialidadeDTO,
     * or with status 400 (Bad Request) if the especialidadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the especialidadeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especialidades")
    @Timed
    public ResponseEntity<EspecialidadeDTO> updateEspecialidade(@Valid @RequestBody EspecialidadeDTO especialidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Especialidade : {}", especialidadeDTO);
        if (especialidadeDTO.getId() == null) {
            return createEspecialidade(especialidadeDTO);
        }
        EspecialidadeDTO result = especialidadeService.save(especialidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, especialidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especialidades : get all the especialidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of especialidades in body
     */
    @GetMapping("/especialidades")
    @Timed
    public ResponseEntity<List<EspecialidadeDTO>> getAllEspecialidades(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Especialidades");
        Page<EspecialidadeDTO> page = especialidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especialidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /especialidades/:id : get the "id" especialidade.
     *
     * @param id the id of the especialidadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especialidadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/especialidades/{id}")
    @Timed
    public ResponseEntity<EspecialidadeDTO> getEspecialidade(@PathVariable Long id) {
        log.debug("REST request to get Especialidade : {}", id);
        EspecialidadeDTO especialidadeDTO = especialidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(especialidadeDTO));
    }

    /**
     * DELETE  /especialidades/:id : delete the "id" especialidade.
     *
     * @param id the id of the especialidadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especialidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspecialidade(@PathVariable Long id) {
        log.debug("REST request to delete Especialidade : {}", id);
        especialidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
