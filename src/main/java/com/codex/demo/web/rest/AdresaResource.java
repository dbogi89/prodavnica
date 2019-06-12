package com.codex.demo.web.rest;

import com.codex.demo.service.AdresaService;
import com.codex.demo.web.rest.errors.BadRequestAlertException;
import com.codex.demo.service.dto.AdresaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.codex.demo.domain.Adresa}.
 */
@RestController
@RequestMapping("/api")
public class AdresaResource {

    private final Logger log = LoggerFactory.getLogger(AdresaResource.class);

    private static final String ENTITY_NAME = "adresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresaService adresaService;

    public AdresaResource(AdresaService adresaService) {
        this.adresaService = adresaService;
    }

    /**
     * {@code POST  /adresas} : Create a new adresa.
     *
     * @param adresaDTO the adresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresaDTO, or with status {@code 400 (Bad Request)} if the adresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresas")
    public ResponseEntity<AdresaDTO> createAdresa(@RequestBody AdresaDTO adresaDTO) throws URISyntaxException {
        log.debug("REST request to save Adresa : {}", adresaDTO);
        if (adresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new adresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdresaDTO result = adresaService.save(adresaDTO);
        return ResponseEntity.created(new URI("/api/adresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresas} : Updates an existing adresa.
     *
     * @param adresaDTO the adresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresaDTO,
     * or with status {@code 400 (Bad Request)} if the adresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresas")
    public ResponseEntity<AdresaDTO> updateAdresa(@RequestBody AdresaDTO adresaDTO) throws URISyntaxException {
        log.debug("REST request to update Adresa : {}", adresaDTO);
        if (adresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdresaDTO result = adresaService.save(adresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adresas} : get all the adresas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresas in body.
     */
    @GetMapping("/adresas")
    public List<AdresaDTO> getAllAdresas() {
        log.debug("REST request to get all Adresas");
        return adresaService.findAll();
    }

    /**
     * {@code GET  /adresas/:id} : get the "id" adresa.
     *
     * @param id the id of the adresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresas/{id}")
    public ResponseEntity<AdresaDTO> getAdresa(@PathVariable Long id) {
        log.debug("REST request to get Adresa : {}", id);
        Optional<AdresaDTO> adresaDTO = adresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adresaDTO);
    }

    /**
     * {@code DELETE  /adresas/:id} : delete the "id" adresa.
     *
     * @param id the id of the adresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresas/{id}")
    public ResponseEntity<Void> deleteAdresa(@PathVariable Long id) {
        log.debug("REST request to delete Adresa : {}", id);
        adresaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
