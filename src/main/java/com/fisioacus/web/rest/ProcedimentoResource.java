package com.fisioacus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fisioacus.service.ProcedimentoService;
import com.fisioacus.web.rest.util.HeaderUtil;
import com.fisioacus.web.rest.util.PaginationUtil;
import com.fisioacus.service.dto.ProcedimentoDTO;
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
 * REST controller for managing Procedimento.
 */
@RestController
@RequestMapping("/api")
public class ProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoResource.class);

    private static final String ENTITY_NAME = "procedimento";

    private final ProcedimentoService procedimentoService;

    public ProcedimentoResource(ProcedimentoService procedimentoService) {
        this.procedimentoService = procedimentoService;
    }

    /**
     * POST  /procedimentos : Create a new procedimento.
     *
     * @param procedimentoDTO the procedimentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedimentoDTO, or with status 400 (Bad Request) if the procedimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedimentos")
    @Timed
    public ResponseEntity<ProcedimentoDTO> createProcedimento(@Valid @RequestBody ProcedimentoDTO procedimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Procedimento : {}", procedimentoDTO);
        if (procedimentoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new procedimento cannot already have an ID")).body(null);
        }
        ProcedimentoDTO result = procedimentoService.save(procedimentoDTO);
        return ResponseEntity.created(new URI("/api/procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedimentos : Updates an existing procedimento.
     *
     * @param procedimentoDTO the procedimentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedimentoDTO,
     * or with status 400 (Bad Request) if the procedimentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the procedimentoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedimentos")
    @Timed
    public ResponseEntity<ProcedimentoDTO> updateProcedimento(@Valid @RequestBody ProcedimentoDTO procedimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Procedimento : {}", procedimentoDTO);
        if (procedimentoDTO.getId() == null) {
            return createProcedimento(procedimentoDTO);
        }
        ProcedimentoDTO result = procedimentoService.save(procedimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedimentos : get all the procedimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedimentos in body
     */
    @GetMapping("/procedimentos")
    @Timed
    public ResponseEntity<List<ProcedimentoDTO>> getAllProcedimentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Procedimentos");
        Page<ProcedimentoDTO> page = procedimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedimentos/:id : get the "id" procedimento.
     *
     * @param id the id of the procedimentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedimentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/procedimentos/{id}")
    @Timed
    public ResponseEntity<ProcedimentoDTO> getProcedimento(@PathVariable Long id) {
        log.debug("REST request to get Procedimento : {}", id);
        ProcedimentoDTO procedimentoDTO = procedimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procedimentoDTO));
    }

    /**
     * DELETE  /procedimentos/:id : delete the "id" procedimento.
     *
     * @param id the id of the procedimentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimento : {}", id);
        procedimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
