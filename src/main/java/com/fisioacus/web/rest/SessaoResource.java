package com.fisioacus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fisioacus.service.SessaoService;
import com.fisioacus.web.rest.util.HeaderUtil;
import com.fisioacus.web.rest.util.PaginationUtil;
import com.fisioacus.service.dto.SessaoDTO;
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
 * REST controller for managing Sessao.
 */
@RestController
@RequestMapping("/api")
public class SessaoResource {

    private final Logger log = LoggerFactory.getLogger(SessaoResource.class);

    private static final String ENTITY_NAME = "sessao";

    private final SessaoService sessaoService;

    public SessaoResource(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    /**
     * POST  /sessaos : Create a new sessao.
     *
     * @param sessaoDTO the sessaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sessaoDTO, or with status 400 (Bad Request) if the sessao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sessaos")
    @Timed
    public ResponseEntity<SessaoDTO> createSessao(@Valid @RequestBody SessaoDTO sessaoDTO) throws URISyntaxException {
        log.debug("REST request to save Sessao : {}", sessaoDTO);
        if (sessaoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sessao cannot already have an ID")).body(null);
        }
        SessaoDTO result = sessaoService.save(sessaoDTO);
        return ResponseEntity.created(new URI("/api/sessaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sessaos : Updates an existing sessao.
     *
     * @param sessaoDTO the sessaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sessaoDTO,
     * or with status 400 (Bad Request) if the sessaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the sessaoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sessaos")
    @Timed
    public ResponseEntity<SessaoDTO> updateSessao(@Valid @RequestBody SessaoDTO sessaoDTO) throws URISyntaxException {
        log.debug("REST request to update Sessao : {}", sessaoDTO);
        if (sessaoDTO.getId() == null) {
            return createSessao(sessaoDTO);
        }
        SessaoDTO result = sessaoService.save(sessaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sessaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sessaos : get all the sessaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sessaos in body
     */
    @GetMapping("/sessaos")
    @Timed
    public ResponseEntity<List<SessaoDTO>> getAllSessaos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Sessaos");
        Page<SessaoDTO> page = sessaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sessaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sessaos/:id : get the "id" sessao.
     *
     * @param id the id of the sessaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sessaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sessaos/{id}")
    @Timed
    public ResponseEntity<SessaoDTO> getSessao(@PathVariable Long id) {
        log.debug("REST request to get Sessao : {}", id);
        SessaoDTO sessaoDTO = sessaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sessaoDTO));
    }

    /**
     * DELETE  /sessaos/:id : delete the "id" sessao.
     *
     * @param id the id of the sessaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sessaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteSessao(@PathVariable Long id) {
        log.debug("REST request to delete Sessao : {}", id);
        sessaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
