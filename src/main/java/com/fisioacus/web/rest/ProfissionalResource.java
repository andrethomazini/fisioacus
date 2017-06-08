package com.fisioacus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fisioacus.service.ProfissionalService;
import com.fisioacus.web.rest.util.HeaderUtil;
import com.fisioacus.web.rest.util.PaginationUtil;
import com.fisioacus.service.dto.ProfissionalDTO;
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
 * REST controller for managing Profissional.
 */
@RestController
@RequestMapping("/api")
public class ProfissionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfissionalResource.class);

    private static final String ENTITY_NAME = "profissional";

    private final ProfissionalService profissionalService;

    public ProfissionalResource(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    /**
     * POST  /profissionals : Create a new profissional.
     *
     * @param profissionalDTO the profissionalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profissionalDTO, or with status 400 (Bad Request) if the profissional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profissionals")
    @Timed
    public ResponseEntity<ProfissionalDTO> createProfissional(@Valid @RequestBody ProfissionalDTO profissionalDTO) throws URISyntaxException {
        log.debug("REST request to save Profissional : {}", profissionalDTO);
        if (profissionalDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profissional cannot already have an ID")).body(null);
        }
        ProfissionalDTO result = profissionalService.save(profissionalDTO);
        return ResponseEntity.created(new URI("/api/profissionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profissionals : Updates an existing profissional.
     *
     * @param profissionalDTO the profissionalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profissionalDTO,
     * or with status 400 (Bad Request) if the profissionalDTO is not valid,
     * or with status 500 (Internal Server Error) if the profissionalDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profissionals")
    @Timed
    public ResponseEntity<ProfissionalDTO> updateProfissional(@Valid @RequestBody ProfissionalDTO profissionalDTO) throws URISyntaxException {
        log.debug("REST request to update Profissional : {}", profissionalDTO);
        if (profissionalDTO.getId() == null) {
            return createProfissional(profissionalDTO);
        }
        ProfissionalDTO result = profissionalService.save(profissionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profissionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profissionals : get all the profissionals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profissionals in body
     */
    @GetMapping("/profissionals")
    @Timed
    public ResponseEntity<List<ProfissionalDTO>> getAllProfissionals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Profissionals");
        Page<ProfissionalDTO> page = profissionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profissionals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profissionals/:id : get the "id" profissional.
     *
     * @param id the id of the profissionalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profissionalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profissionals/{id}")
    @Timed
    public ResponseEntity<ProfissionalDTO> getProfissional(@PathVariable Long id) {
        log.debug("REST request to get Profissional : {}", id);
        ProfissionalDTO profissionalDTO = profissionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profissionalDTO));
    }

    /**
     * DELETE  /profissionals/:id : delete the "id" profissional.
     *
     * @param id the id of the profissionalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profissionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfissional(@PathVariable Long id) {
        log.debug("REST request to delete Profissional : {}", id);
        profissionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
