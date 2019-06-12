package com.codex.demo.service;

import com.codex.demo.service.dto.AdresaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.codex.demo.domain.Adresa}.
 */
public interface AdresaService {

    /**
     * Save a adresa.
     *
     * @param adresaDTO the entity to save.
     * @return the persisted entity.
     */
    AdresaDTO save(AdresaDTO adresaDTO);

    /**
     * Get all the adresas.
     *
     * @return the list of entities.
     */
    List<AdresaDTO> findAll();


    /**
     * Get the "id" adresa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdresaDTO> findOne(Long id);

    /**
     * Delete the "id" adresa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
