package com.codex.demo.service;

import com.codex.demo.domain.Artikal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Artikal}.
 */
public interface ArtikalService {

    /**
     * Save a artikal.
     *
     * @param artikal the entity to save.
     * @return the persisted entity.
     */
    Artikal save(Artikal artikal);

    /**
     * Get all the artikals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Artikal> findAll(Pageable pageable);


    /**
     * Get the "id" artikal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Artikal> findOne(Long id);

    /**
     * Delete the "id" artikal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
