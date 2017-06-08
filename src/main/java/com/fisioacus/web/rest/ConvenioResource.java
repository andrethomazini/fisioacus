package com.fisioacus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fisioacus.service.ConvenioService;
import com.fisioacus.web.rest.util.HeaderUtil;
import com.fisioacus.web.rest.util.PaginationUtil;
import com.fisioacus.service.dto.ConvenioDTO;
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
 * REST controller for managing Convenio.
 */
@RestController
@RequestMapping("/api")
public class ConvenioResource {

    private final Logger log = LoggerFactory.getLogger(ConvenioResource.class);

    private static final String ENTITY_NAME = "convenio";

    private final ConvenioService convenioService;

    public ConvenioResource(ConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    /**
     * POST  /convenios : Create a new convenio.
     *
     * @param convenioDTO the convenioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new convenioDTO, or with status 400 (Bad Request) if the convenio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/convenios")
    @Timed
    public ResponseEntity<ConvenioDTO> createConvenio(@Valid @RequestBody ConvenioDTO convenioDTO) throws URISyntaxException {
        log.debug("REST request to save Convenio : {}", convenioDTO);
        if (convenioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new convenio cannot already have an ID")).body(null);
        }
        ConvenioDTO result = convenioService.save(convenioDTO);
        return ResponseEntity.created(new URI("/api/convenios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /convenios : Updates an existing convenio.
     *
     * @param convenioDTO the convenioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated convenioDTO,
     * or with status 400 (Bad Request) if the convenioDTO is not valid,
     * or with status 500 (Internal Server Error) if the convenioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/convenios")
    @Timed
    public ResponseEntity<ConvenioDTO> updateConvenio(@Valid @RequestBody ConvenioDTO convenioDTO) throws URISyntaxException {
        log.debug("REST request to update Convenio : {}", convenioDTO);
        if (convenioDTO.getId() == null) {
            return createConvenio(convenioDTO);
        }
        ConvenioDTO result = convenioService.save(convenioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, convenioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /convenios : get all the convenios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of convenios in body
     */
    @GetMapping("/convenios")
    @Timed
    public ResponseEntity<List<ConvenioDTO>> getAllConvenios(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Convenios");
        Page<ConvenioDTO> page = convenioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/convenios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /convenios/:id : get the "id" convenio.
     *
     * @param id the id of the convenioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the convenioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/convenios/{id}")
    @Timed
    public ResponseEntity<ConvenioDTO> getConvenio(@PathVariable Long id) {
        log.debug("REST request to get Convenio : {}", id);
        ConvenioDTO convenioDTO = convenioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(convenioDTO));
    }

    /**
     * DELETE  /convenios/:id : delete the "id" convenio.
     *
     * @param id the id of the convenioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/convenios/{id}")
    @Timed
    public ResponseEntity<Void> deleteConvenio(@PathVariable Long id) {
        log.debug("REST request to delete Convenio : {}", id);
        convenioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
