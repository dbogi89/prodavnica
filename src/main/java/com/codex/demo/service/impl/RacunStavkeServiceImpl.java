package com.codex.demo.service.impl;

import com.codex.demo.service.RacunStavkeService;
import com.codex.demo.domain.RacunStavke;
import com.codex.demo.repository.RacunStavkeRepository;
import com.codex.demo.service.dto.RacunStavkeDTO;
import com.codex.demo.service.mapper.RacunStavkeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RacunStavke}.
 */
@Service
@Transactional
public class RacunStavkeServiceImpl implements RacunStavkeService {

    private final Logger log = LoggerFactory.getLogger(RacunStavkeServiceImpl.class);

    private final RacunStavkeRepository racunStavkeRepository;

    private final RacunStavkeMapper racunStavkeMapper;

    public RacunStavkeServiceImpl(RacunStavkeRepository racunStavkeRepository, RacunStavkeMapper racunStavkeMapper) {
        this.racunStavkeRepository = racunStavkeRepository;
        this.racunStavkeMapper = racunStavkeMapper;
    }

    /**
     * Save a racunStavke.
     *
     * @param racunStavkeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RacunStavkeDTO save(RacunStavkeDTO racunStavkeDTO) {
        log.debug("Request to save RacunStavke : {}", racunStavkeDTO);
        RacunStavke racunStavke = racunStavkeMapper.toEntity(racunStavkeDTO);
        racunStavke = racunStavkeRepository.save(racunStavke);
        return racunStavkeMapper.toDto(racunStavke);
    }

    /**
     * Get all the racunStavkes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RacunStavkeDTO> findAll() {
        log.debug("Request to get all RacunStavkes");
        return racunStavkeRepository.findAll().stream()
            .map(racunStavkeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one racunStavke by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RacunStavkeDTO> findOne(Long id) {
        log.debug("Request to get RacunStavke : {}", id);
        return racunStavkeRepository.findById(id)
            .map(racunStavkeMapper::toDto);
    }

    /**
     * Delete the racunStavke by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RacunStavke : {}", id);
        racunStavkeRepository.deleteById(id);
    }
}
