package com.codex.demo.web.rest;

import com.codex.demo.service.RacunService;
import com.codex.demo.web.rest.errors.BadRequestAlertException;
import com.codex.demo.service.dto.RacunDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.codex.demo.domain.Racun}.
 */
@RestController
@RequestMapping("/api")
public class RacunResource {

    private final Logger log = LoggerFactory.getLogger(RacunResource.class);

    private static final String ENTITY_NAME = "racun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacunService racunService;

    public RacunResource(RacunService racunService) {
        this.racunService = racunService;
    }

    /**
     * {@code POST  /racuns} : Create a new racun.
     *
     * @param racunDTO the racunDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racunDTO, or with status {@code 400 (Bad Request)} if the racun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racuns")
    public ResponseEntity<RacunDTO> createRacun(@RequestBody RacunDTO racunDTO) throws URISyntaxException {
        log.debug("REST request to save Racun : {}", racunDTO);
        if (racunDTO.getId() != null) {
            throw new BadRequestAlertException("A new racun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacunDTO result = racunService.save(racunDTO);
        return ResponseEntity.created(new URI("/api/racuns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racuns} : Updates an existing racun.
     *
     * @param racunDTO the racunDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racunDTO,
     * or with status {@code 400 (Bad Request)} if the racunDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racunDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racuns")
    public ResponseEntity<RacunDTO> updateRacun(@RequestBody RacunDTO racunDTO) throws URISyntaxException {
        log.debug("REST request to update Racun : {}", racunDTO);
        if (racunDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacunDTO result = racunService.save(racunDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, racunDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racuns} : get all the racuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racuns in body.
     */
    @GetMapping("/racuns")
    public ResponseEntity<List<RacunDTO>> getAllRacuns(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Racuns");
        Page<RacunDTO> page = racunService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /racuns/:id} : get the "id" racun.
     *
     * @param id the id of the racunDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racunDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racuns/{id}")
    public ResponseEntity<RacunDTO> getRacun(@PathVariable Long id) {
        log.debug("REST request to get Racun : {}", id);
        Optional<RacunDTO> racunDTO = racunService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racunDTO);
    }

    /**
     * {@code DELETE  /racuns/:id} : delete the "id" racun.
     *
     * @param id the id of the racunDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racuns/{id}")
    public ResponseEntity<Void> deleteRacun(@PathVariable Long id) {
        log.debug("REST request to delete Racun : {}", id);
        racunService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
