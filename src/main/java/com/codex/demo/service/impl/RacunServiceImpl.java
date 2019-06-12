package com.codex.demo.service.impl;

import com.codex.demo.service.RacunService;
import com.codex.demo.domain.Racun;
import com.codex.demo.repository.RacunRepository;
import com.codex.demo.service.dto.RacunDTO;
import com.codex.demo.service.mapper.RacunMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Racun}.
 */
@Service
@Transactional
public class RacunServiceImpl implements RacunService {

    private final Logger log = LoggerFactory.getLogger(RacunServiceImpl.class);

    private final RacunRepository racunRepository;

    private final RacunMapper racunMapper;

    public RacunServiceImpl(RacunRepository racunRepository, RacunMapper racunMapper) {
        this.racunRepository = racunRepository;
        this.racunMapper = racunMapper;
    }

    /**
     * Save a racun.
     *
     * @param racunDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RacunDTO save(RacunDTO racunDTO) {
        log.debug("Request to save Racun : {}", racunDTO);
        Racun racun = racunMapper.toEntity(racunDTO);
        racun = racunRepository.save(racun);
        return racunMapper.toDto(racun);
    }

    /**
     * Get all the racuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RacunDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Racuns");
        return racunRepository.findAll(pageable)
            .map(racunMapper::toDto);
    }


    /**
     * Get one racun by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RacunDTO> findOne(Long id) {
        log.debug("Request to get Racun : {}", id);
        return racunRepository.findById(id)
            .map(racunMapper::toDto);
    }

    /**
     * Delete the racun by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Racun : {}", id);
        racunRepository.deleteById(id);
    }
}
