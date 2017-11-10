package com.fisioacus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fisioacus.service.AtendimentoService;
import com.fisioacus.web.rest.util.HeaderUtil;
import com.fisioacus.web.rest.util.PaginationUtil;
import com.fisioacus.service.dto.AtendimentoDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Atendimento.
 */
@RestController
@RequestMapping("/api")
public class AtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(AtendimentoResource.class);

    private static final String ENTITY_NAME = "atendimento";

    private final AtendimentoService atendimentoService;

    public AtendimentoResource(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    /**
     * POST  /atendimentos : Create a new atendimento.
     *
     * @param atendimentoDTO the atendimentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new atendimentoDTO, or with status 400 (Bad Request) if the atendimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/atendimentos")
    @Timed
    public ResponseEntity<AtendimentoDTO> createAtendimento(@RequestBody AtendimentoDTO atendimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Atendimento : {}", atendimentoDTO);
        if (atendimentoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new atendimento cannot already have an ID")).body(null);
        }
        AtendimentoDTO result = atendimentoService.save(atendimentoDTO);
        return ResponseEntity.created(new URI("/api/atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /atendimentos : Updates an existing atendimento.
     *
     * @param atendimentoDTO the atendimentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated atendimentoDTO,
     * or with status 400 (Bad Request) if the atendimentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the atendimentoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/atendimentos")
    @Timed
    public ResponseEntity<AtendimentoDTO> updateAtendimento(@RequestBody AtendimentoDTO atendimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Atendimento : {}", atendimentoDTO);
        if (atendimentoDTO.getId() == null) {
            return createAtendimento(atendimentoDTO);
        }
        AtendimentoDTO result = atendimentoService.save(atendimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, atendimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /atendimentos : get all the atendimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of atendimentos in body
     */
    @GetMapping("/atendimentos")
    @Timed
    public ResponseEntity<List<AtendimentoDTO>> getAllAtendimentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Atendimentos");
        Page<AtendimentoDTO> page = atendimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atendimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /atendimentos/:id : get the "id" atendimento.
     *
     * @param id the id of the atendimentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the atendimentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/atendimentos/{id}")
    @Timed
    public ResponseEntity<AtendimentoDTO> getAtendimento(@PathVariable Long id) {
        log.debug("REST request to get Atendimento : {}", id);
        AtendimentoDTO atendimentoDTO = atendimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(atendimentoDTO));
    }

    /**
     * DELETE  /atendimentos/:id : delete the "id" atendimento.
     *
     * @param id the id of the atendimentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/atendimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete Atendimento : {}", id);
        atendimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
