package com.codex.demo.service.impl;

import com.codex.demo.service.ArtikalService;
import com.codex.demo.domain.Artikal;
import com.codex.demo.repository.ArtikalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Artikal}.
 */
@Service
@Transactional
public class ArtikalServiceImpl implements ArtikalService {

    private final Logger log = LoggerFactory.getLogger(ArtikalServiceImpl.class);

    private final ArtikalRepository artikalRepository;

    public ArtikalServiceImpl(ArtikalRepository artikalRepository) {
        this.artikalRepository = artikalRepository;
    }

    /**
     * Save a artikal.
     *
     * @param artikal the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Artikal save(Artikal artikal) {
        log.debug("Request to save Artikal : {}", artikal);
        return artikalRepository.save(artikal);
    }

    /**
     * Get all the artikals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Artikal> findAll(Pageable pageable) {
        log.debug("Request to get all Artikals");
        return artikalRepository.findAll(pageable);
    }


    /**
     * Get one artikal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Artikal> findOne(Long id) {
        log.debug("Request to get Artikal : {}", id);
        return artikalRepository.findById(id);
    }

    /**
     * Delete the artikal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artikal : {}", id);
        artikalRepository.deleteById(id);
    }
}
