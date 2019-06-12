package com.codex.demo.web.rest;

import com.codex.demo.service.RacunStavkeService;
import com.codex.demo.web.rest.errors.BadRequestAlertException;
import com.codex.demo.service.dto.RacunStavkeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.codex.demo.domain.RacunStavke}.
 */
@RestController
@RequestMapping("/api")
public class RacunStavkeResource {

    private final Logger log = LoggerFactory.getLogger(RacunStavkeResource.class);

    private static final String ENTITY_NAME = "racunStavke";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacunStavkeService racunStavkeService;

    public RacunStavkeResource(RacunStavkeService racunStavkeService) {
        this.racunStavkeService = racunStavkeService;
    }

    /**
     * {@code POST  /racun-stavkes} : Create a new racunStavke.
     *
     * @param racunStavkeDTO the racunStavkeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racunStavkeDTO, or with status {@code 400 (Bad Request)} if the racunStavke has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racun-stavkes")
    public ResponseEntity<RacunStavkeDTO> createRacunStavke(@Valid @RequestBody RacunStavkeDTO racunStavkeDTO) throws URISyntaxException {
        log.debug("REST request to save RacunStavke : {}", racunStavkeDTO);
        if (racunStavkeDTO.getId() != null) {
            throw new BadRequestAlertException("A new racunStavke cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacunStavkeDTO result = racunStavkeService.save(racunStavkeDTO);
        return ResponseEntity.created(new URI("/api/racun-stavkes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racun-stavkes} : Updates an existing racunStavke.
     *
     * @param racunStavkeDTO the racunStavkeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racunStavkeDTO,
     * or with status {@code 400 (Bad Request)} if the racunStavkeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racunStavkeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racun-stavkes")
    public ResponseEntity<RacunStavkeDTO> updateRacunStavke(@Valid @RequestBody RacunStavkeDTO racunStavkeDTO) throws URISyntaxException {
        log.debug("REST request to update RacunStavke : {}", racunStavkeDTO);
        if (racunStavkeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacunStavkeDTO result = racunStavkeService.save(racunStavkeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, racunStavkeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racun-stavkes} : get all the racunStavkes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racunStavkes in body.
     */
    @GetMapping("/racun-stavkes")
    public List<RacunStavkeDTO> getAllRacunStavkes() {
        log.debug("REST request to get all RacunStavkes");
        return racunStavkeService.findAll();
    }

    /**
     * {@code GET  /racun-stavkes/:id} : get the "id" racunStavke.
     *
     * @param id the id of the racunStavkeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racunStavkeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racun-stavkes/{id}")
    public ResponseEntity<RacunStavkeDTO> getRacunStavke(@PathVariable Long id) {
        log.debug("REST request to get RacunStavke : {}", id);
        Optional<RacunStavkeDTO> racunStavkeDTO = racunStavkeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racunStavkeDTO);
    }

    /**
     * {@code DELETE  /racun-stavkes/:id} : delete the "id" racunStavke.
     *
     * @param id the id of the racunStavkeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racun-stavkes/{id}")
    public ResponseEntity<Void> deleteRacunStavke(@PathVariable Long id) {
        log.debug("REST request to delete RacunStavke : {}", id);
        racunStavkeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
