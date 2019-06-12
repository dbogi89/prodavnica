package com.codex.demo.service;

import com.codex.demo.service.dto.RacunStavkeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.codex.demo.domain.RacunStavke}.
 */
public interface RacunStavkeService {

    /**
     * Save a racunStavke.
     *
     * @param racunStavkeDTO the entity to save.
     * @return the persisted entity.
     */
    RacunStavkeDTO save(RacunStavkeDTO racunStavkeDTO);

    /**
     * Get all the racunStavkes.
     *
     * @return the list of entities.
     */
    List<RacunStavkeDTO> findAll();


    /**
     * Get the "id" racunStavke.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RacunStavkeDTO> findOne(Long id);

    /**
     * Delete the "id" racunStavke.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
