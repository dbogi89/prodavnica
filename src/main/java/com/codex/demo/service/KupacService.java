package com.codex.demo.service;

import com.codex.demo.domain.Kupac;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Kupac}.
 */
public interface KupacService {

    /**
     * Save a kupac.
     *
     * @param kupac the entity to save.
     * @return the persisted entity.
     */
    Kupac save(Kupac kupac);

    /**
     * Get all the kupacs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Kupac> findAll(Pageable pageable);


    /**
     * Get the "id" kupac.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Kupac> findOne(Long id);

    /**
     * Delete the "id" kupac.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
