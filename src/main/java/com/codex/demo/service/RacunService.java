package com.codex.demo.service;

import com.codex.demo.service.dto.RacunDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.codex.demo.domain.Racun}.
 */
public interface RacunService {

    /**
     * Save a racun.
     *
     * @param racunDTO the entity to save.
     * @return the persisted entity.
     */
    RacunDTO save(RacunDTO racunDTO);

    /**
     * Get all the racuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RacunDTO> findAll(Pageable pageable);


    /**
     * Get the "id" racun.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RacunDTO> findOne(Long id);

    /**
     * Delete the "id" racun.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
